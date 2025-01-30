package io.droidevs.wallpaper.coil.wallpaper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.collection.LruCache
import androidx.collection.lruCache
import coil3.asImage
import coil3.decode.DataSource
import coil3.intercept.Interceptor
import coil3.request.ImageResult
import coil3.request.SuccessResult
import io.droidevs.wallpaper.domain.Wallpaper

class WallpaperCacheInterceptor(
    private val context : Context,
    private val cache : LruCache<String, Drawable> = lruCache(30)
) : Interceptor{
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val wallpaperUri = chain.request.data as String
        val cached = cache.get(wallpaperUri)

        cached?.let {
            return SuccessResult(
                image = cached.asImage(),
                request = chain.request,
                dataSource = DataSource.MEMORY
            )
        }

        return chain.proceed()
    }
}