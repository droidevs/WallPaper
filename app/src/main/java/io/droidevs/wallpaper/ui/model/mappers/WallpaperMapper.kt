package io.droidevs.wallpaper.ui.model.mappers

import io.droidevs.wallpaper.domain.model.RemoteWallpaper
import io.droidevs.wallpaper.ui.model.RemoteWallpaperUi


fun RemoteWallpaper.toUi(): RemoteWallpaperUi {
    return RemoteWallpaperUi(
        id = id,
        remoteId = remoteId,
        date = createdAt,
        dimensionsText = "$width × $height",
        color = color,
        blurHash = blurHash,
        likesText = "$likes likes",
        description = description,
        url = url,
        thumbUrl = thumbUrl,
        aspectRatio = aspectRatio,
    )
}