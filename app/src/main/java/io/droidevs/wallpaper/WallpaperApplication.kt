package io.droidevs.wallpaper

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import io.droidevs.wallpaper.coil.wallpaper.WallpaperImageLoader

@HiltAndroidApp
class WallpaperApplication() : Application() , SingletonImageLoader.Factory {


    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return WallpaperImageLoader.newImageLoader(context)
    }
}