package io.droidevs.wallpaper.data.system

import android.app.WallpaperManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import io.droidevs.wallpaper.data.image.internal.BitmapProcessorService
import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.model.Screen
import io.droidevs.wallpaper.domain.model.ScreenDimens
import io.droidevs.wallpaper.domain.services.WallpaperSetUpService
import kotlinx.io.IOException

class WallpaperSetUpServiceImpl(
    private val context: Context,
    private val bitmapProcessorService: BitmapProcessorService
) : WallpaperSetUpService, BitmapProcessorService by bitmapProcessorService {
    override suspend fun applyWallpaper(image: ImageData): Boolean {
        val wallpaperManager = WallpaperManager.getInstance(context)
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                !wallpaperManager.isWallpaperSupported
            } else {
                false
            }
        ) return false
        try {
            processBitmap(image){ bitmap, exifData ->
                wallpaperManager.setBitmap(bitmap)
                bitmap
            }
            return true
        }catch (e: IOException){
            return false
        }
    }

    override suspend fun applyWallpaper(image: ImageData, dimens: ScreenDimens) : Boolean{
        try {
            val wallpaperManager = WallpaperManager.getInstance(context)
            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    !wallpaperManager.isWallpaperSupported
                } else {
                    false
                }
            ) return false

            processBitmap(image){ bitmap,_ ->
                wallpaperManager.suggestDesiredDimensions(dimens.width, dimens.height)
                wallpaperManager.setBitmap(bitmap)
                bitmap
            }
            return true
        }catch (e: IOException){
            return false
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun applyWallpaper(image: ImageData, screen: Screen): Boolean {
        try {
            val wallpaperManager = WallpaperManager.getInstance(context)

            if (!wallpaperManager.isWallpaperSupported) return false

            processBitmap(image){ bitmap, exifData ->
                if (screen in listOf(Screen.BOTH, Screen.HOME)) {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                }
                if (screen in listOf(Screen.BOTH, Screen.LOCK)) {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                }
                bitmap
            }
            return true
        } catch (e: IOException) {
            return false
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun applyWallpaper(image: ImageData, screen: Screen, dimens: ScreenDimens): Boolean {
        try {
            val wallpaperManager = WallpaperManager.getInstance(context)

            if (!wallpaperManager.isWallpaperSupported) return false

            wallpaperManager.suggestDesiredDimensions(dimens.width,dimens.height)

            processBitmap(image){ bitmap,_ ->
                if (screen in listOf(Screen.BOTH, Screen.HOME)) {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                }
                if (screen in listOf(Screen.BOTH, Screen.LOCK)) {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                }
                bitmap
            }
            return true
        } catch (e: IOException) {
            return false
        }
    }

}