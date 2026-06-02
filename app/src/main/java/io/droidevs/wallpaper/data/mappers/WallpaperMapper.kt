package io.droidevs.wallpaper.data.mappers

import io.droidevs.wallpaper.data.local.RemoteWallpaperEntity
import io.droidevs.wallpaper.data.network.dtos.PhotoDto

fun PhotoDto.toDomain(): RemoteWallpaperEntity {
    return RemoteWallpaperEntity(
        remoteId = id,
        createdAt = createdAt.toMillisOrNull()?: -1,
        updatedAt = updatedAt.toMillisOrNull()?: -1,
        width = width,
        height = height,
        color = color,
        blurHash = blurHash,
        downloads = downloads,
        likes = likes,
        description = description,
        url = urls.regular,
        thumbUrl = urls.thumb
    )
}
