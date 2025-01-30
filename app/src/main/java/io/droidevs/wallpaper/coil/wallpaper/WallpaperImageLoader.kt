package io.droidevs.wallpaper.coil.wallpaper

import android.content.Context
import coil3.ComponentRegistry
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import io.droidevs.wallpaper.coil.Cache
import io.droidevs.wallpaper.domain.Wallpaper

object WallpaperImageLoader {


    fun newImageLoader(context: Context) : ImageLoader {
        return ImageLoader(context).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context,0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.01)
                    .directory(context.cacheDir)
                    .build()
            }
            .components {
                ComponentRegistry.Builder()
                    .add(WallpaperCacheInterceptor(context, cache = Cache.data))
                    .add(WallpaperMapper())
            }
            .logger(DebugLogger())
            .build()
    }
}