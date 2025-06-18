package io.droidevs.wallpaper.domain.mappers

import io.droidevs.wallpaper.data.local.CollectionEntity
import io.droidevs.wallpaper.domain.model.Collection


fun Collection.toEntity() : CollectionEntity {
    return CollectionEntity(
        id, id,
        title = title,
        description = description,
        totalPhotos = totalPhotos,
        coverUrl = coverUrl,
        coverWidth = coverWidth,
        coverHeight = coverHeight,
        publishTime = publishTime,
        updateTime = updateTime
    )
}