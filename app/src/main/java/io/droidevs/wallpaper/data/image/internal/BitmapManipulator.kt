package io.droidevs.wallpaper.data.image.internal

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import androidx.datastore.preferences.protobuf.FloatValue
import androidx.palette.graphics.Palette
import io.droidevs.wallpaper.domain.services.ColorMatrixUseCasesService
import javax.inject.Inject

// domain/repository/BitmapManipulator.kt
interface BitmapManipulator {
    fun applyContrast(bitmap: Bitmap, contrast: Float): Bitmap
    fun applySaturation(bitmap: Bitmap, saturation: Float): Bitmap
    fun applyBrightness(bitmap: Bitmap, brightness: Float): Bitmap
    fun applyHue(bitmap: Bitmap, red: Float, green: Float, blue: Float): Bitmap
    fun applyScale(bitmap: Bitmap, redScale: Float, greenScale: Float, blueScale: Float): Bitmap
    fun cropBitmap(bitmap: Bitmap, rect: Rect): Bitmap
    fun rotateImage(bitmap: Bitmap, degrees: Float): Bitmap
    fun calculateBrightnessEstimate(bitmap: Bitmap, pixelSpacing: Int = 20): Float
    fun generatePalette(bitmap: Bitmap): Palette

    fun applyColorMatrix(bitmap: Bitmap, colorMatrix: ColorMatrix): Bitmap

    fun applyColorMatrix(bitmap: Bitmap, colorMatrix: List<ColorMatrix>): Bitmap
}

// data/media/image/internal/BitmapManipulator.kt
open class BitmapManipulatorImpl @Inject constructor(
    private val colorMatrixUseCases: ColorMatrixUseCasesService
): BitmapManipulator, ColorMatrixUseCasesService by colorMatrixUseCases {
    override fun applyContrast(bitmap: Bitmap, contrast: Float): Bitmap {
        return applyColorMatrix(bitmap, ColorMatrix(createContrastMatrix(contrast)))
    }

    override fun applySaturation(bitmap: Bitmap, saturation: Float): Bitmap {
        return applyColorMatrix(bitmap, ColorMatrix(createSaturationMatrix(saturation)))
    }

    override fun applyBrightness(bitmap: Bitmap, brightness: Float): Bitmap {
        return applyColorMatrix(bitmap, ColorMatrix(createBrightnessMatrix(brightness)))
    }

    override fun applyHue(bitmap: Bitmap, red: Float, green: Float, blue: Float): Bitmap {
        return applyColorMatrix(bitmap, ColorMatrix(createHueMatrix(red, green, blue)))
    }

    override fun applyScale(bitmap: Bitmap, redScale: Float, greenScale: Float, blueScale: Float): Bitmap {
        return applyColorMatrix(bitmap, ColorMatrix(createScaleMatrix(redScale, greenScale, blueScale)))
    }

    override fun applyColorMatrix(bitmap: Bitmap, colorMatrix: ColorMatrix): Bitmap {
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }
        val result = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height,
            bitmap.config ?: Bitmap.Config.ARGB_8888
        )
        Canvas(result).apply {
            drawBitmap(bitmap, 0f, 0f, paint)
        }
        return result
    }

    override fun applyColorMatrix(bitmap: Bitmap, colorMatrix: List<ColorMatrix>): Bitmap {
        return applyColorMatrix(
            bitmap,
            ColorMatrix(
                combineMatrices(colorMatrix.map {
                    it.array
                })
            )
        )
    }

    // Non-matrix operations remain unchanged
    override fun cropBitmap(bitmap: Bitmap, rect: Rect): Bitmap {
        return Bitmap.createBitmap(
            bitmap,
            rect.left,
            rect.top,
            rect.width(),
            rect.height()
        )
    }

    override fun rotateImage(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
        )
    }

    override fun calculateBrightnessEstimate(bitmap: Bitmap, pixelSpacing: Int): Float {
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var pixelsRead = 0
        var totalBrightness = 0
        for (i in pixels.indices step pixelSpacing) {
            totalBrightness += Color.red(pixels[i]) +
                    Color.green(pixels[i]) +
                    Color.blue(pixels[i])
            pixelsRead++
        }

        return totalBrightness.toFloat() / (pixelsRead * 3) / 256
    }

    override fun generatePalette(bitmap: Bitmap): Palette {
        return Palette.from(bitmap).generate()
    }
}