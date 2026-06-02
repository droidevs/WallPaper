package io.droidevs.wallpaper.domain.mappers

import io.droidevs.wallpaper.data.local.RemoteWallpaperEntity
import io.droidevs.wallpaper.domain.model.RemoteWallpaper


// Entity to Domain
fun RemoteWallpaperEntity.toDomain(): RemoteWallpaper {
    return RemoteWallpaper(
        id = id,
        remoteId = remoteId,
        createdAt = createdAt,
        updatedAt = updatedAt,
        width = width,
        height = height,
        color = color,
        blurHash = blurHash,
        downloads = downloads,
        likes = likes,
        description = description,
        url = url,
        thumbUrl = thumbUrl
    )
}