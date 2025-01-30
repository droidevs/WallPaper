package io.droidevs.wallpaper.coil.wallpaper

import android.content.Context
import coil3.asDrawable
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import io.droidevs.wallpaper.coil.Cache
import io.droidevs.wallpaper.domain.Wallpaper


object WallpaperImageRequest {


    fun newImageRequest(context: Context,wallpaper: Wallpaper) : ImageRequest{
        return ImageRequest.Builder(context).apply {
            data(wallpaper)
            crossfade(true)
            transformations(CircleCropTransformation())
            listener(
                onSuccess = { request, metadata ->
                    Cache.data.put(
                        metadata.request.data as String,
                        metadata.image.asDrawable(context.resources)
                    )
                }
            )
        }.build()
    }
}