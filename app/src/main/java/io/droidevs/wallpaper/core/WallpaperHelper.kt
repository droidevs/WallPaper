package io.droidevs.wallpaper.core

import io.droidevs.wallpaper.domain.model.Effect
import io.droidevs.wallpaper.domain.model.ResizeMethod
import io.droidevs.wallpaper.domain.model.Screen

import android.app.WallpaperManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WallpaperHelper {
    private const val LIGHT_BRIGHTNESS_MIN = 0.8f
    private const val DARK_BRIGHTNESS_MAX = 0.3f

    private const val TARGET_BRIGHTNESS_DARK = 0.7f
    private const val TARGET_BRIGHTNESS_LIGHT = 0.4f

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setWallpaperUp(context: Context, imageBitmap: Bitmap, mode: Int) {
        val (width, height) = getMetrics(context)
        val wallpaperManager = WallpaperManager.getInstance(context)
        wallpaperManager.suggestDesiredDimensions(width, height)

        if (!wallpaperManager.isWallpaperSupported) return
        wallpaperManager.setBitmap(imageBitmap, null, true, mode)
    }

    private fun setWallpaperLegacy(context: Context, imageBitmap: Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(context)
        wallpaperManager.setBitmap(imageBitmap)
    }

    suspend fun setWallpaperWithFilters(context: Context, src: Bitmap, effect: Effect, mode: Screen, resizeMethod: ResizeMethod, autoLightenDarken: Boolean) {
        withContext(Dispatchers.IO) {
            var bitmap = BitmapProcessor.applyEffect(src,effect)
            bitmap = resizeBitmapByPreference(context, bitmap = bitmap, resizeMethod = resizeMethod)
            if (autoLightenDarken) {
                bitmap = lightenOrDarkenBitmapIfNeeded(context, bitmap)
            }
            setWallpaper(context, bitmap, mode)
        }
    }

    suspend fun setWallpaperWithoutFilters(context: Context, src: Bitmap, mode: Screen, resizeMethod: ResizeMethod) {
        withContext(Dispatchers.IO) {
            val bitmap = resizeBitmapByPreference(context = context, bitmap = src, resizeMethod = resizeMethod)
            setWallpaper(context, bitmap, mode)
        }
    }

    fun setWallpaper(context: Context, bitmap: Bitmap, mode: Screen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (mode in listOf(Screen.BOTH, Screen.HOME)) {
                setWallpaperUp(context, bitmap, WallpaperManager.FLAG_SYSTEM)
            }
            if (mode in listOf(Screen.BOTH, Screen.LOCK)) {
                setWallpaperUp(context, bitmap, WallpaperManager.FLAG_LOCK)
            }
        } else {
            setWallpaperLegacy(context, bitmap)
        }
    }


    private fun lightenOrDarkenBitmapIfNeeded(context: Context, bitmap: Bitmap): Bitmap {
        val bitmapBrightness = bitmap.calculateBrightnessEstimate()
        val isDarkMode = ThemeHelper.isNightMode(context)

        return when {
            isDarkMode && bitmapBrightness > LIGHT_BRIGHTNESS_MIN -> {
                val newBrightness = TARGET_BRIGHTNESS_DARK / bitmapBrightness
                bitmap.applyBrightness(newBrightness)
            }
            !isDarkMode && bitmapBrightness < DARK_BRIGHTNESS_MAX -> {
                val newBrightness = TARGET_BRIGHTNESS_LIGHT / bitmapBrightness
                bitmap.applyBrightness(newBrightness)
            }
            else -> bitmap
        }
    }

    private fun getMetrics(context: Context): Pair<Int, Int> {
        val metrics = context.resources.displayMetrics
        return metrics.widthPixels to metrics.heightPixels
    }

    private fun resizeBitmapByPreference(
        context: Context,
        bitmap: Bitmap,
        resizeMethod: ResizeMethod
    ): Bitmap {
        if (resizeMethod == ResizeMethod.NONE) return bitmap
        val (targetW, targetH) = getMetrics(context)
        return when (resizeMethod) {
            ResizeMethod.FIT_WIDTH -> {
                val scale = targetW.toFloat() / bitmap.width
                Bitmap.createScaledBitmap(bitmap, targetW, (bitmap.height * scale).toInt(), true)
            }
            ResizeMethod.FIT_HEIGHT -> {
                val scale = targetH.toFloat() / bitmap.height
                Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), targetH, true)
            }
            ResizeMethod.CROP, ResizeMethod.ZOOM -> {
                val scale = maxOf(
                    targetW.toFloat() / bitmap.width,
                    targetH.toFloat() / bitmap.height
                )
                val scaled = Bitmap.createScaledBitmap(
                    bitmap,
                    (bitmap.width * scale).toInt(),
                    (bitmap.height * scale).toInt(),
                    true
                )
                val x = (scaled.width - targetW) / 2
                val y = (scaled.height - targetH) / 2
                Bitmap.createBitmap(scaled, x, y, targetW, targetH)
            }
            ResizeMethod.NONE -> bitmap
        }
    }

    private fun Bitmap.calculateBrightnessEstimate(pixelSpacing: Int = 20): Float {
        if (width == 0 || height == 0) return 0f
        val pixels = IntArray(width * height)
        getPixels(pixels, 0, width, 0, 0, width, height)
        var pixelsRead = 0
        var totalBrightness = 0
        for (i in pixels.indices step pixelSpacing) {
            val c = pixels[i]
            totalBrightness += android.graphics.Color.red(c) +
                android.graphics.Color.green(c) +
                android.graphics.Color.blue(c)
            pixelsRead++
        }
        return totalBrightness.toFloat() / (pixelsRead * 3) / 255f
    }

    private fun Bitmap.applyBrightness(brightness: Float): Bitmap {
        val cm = ColorMatrix().apply {
            setScale(brightness, brightness, brightness, 1f)
        }
        val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(cm) }
        val result = Bitmap.createBitmap(width, height, config ?: Bitmap.Config.ARGB_8888)
        Canvas(result).drawBitmap(this, 0f, 0f, paint)
        return result
    }

    private object ThemeHelper {
        fun isNightMode(context: Context): Boolean {
            val nightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return nightMode == Configuration.UI_MODE_NIGHT_YES
        }
    }
}