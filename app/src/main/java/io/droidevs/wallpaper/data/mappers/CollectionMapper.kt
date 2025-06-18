package io.droidevs.wallpaper.data.mappers

import io.droidevs.wallpaper.data.local.CollectionEntity
import io.droidevs.wallpaper.data.network.dtos.CollectionDto
import io.droidevs.wallpaper.domain.model.Collection
import io.droidevs.wallpaper.ui.ex.IsoToLong


fun CollectionEntity.toDomain(): Collection{
    return Collection(
        id = id,
        title = title,
        description = description,
        totalPhotos = totalPhotos,
        coverUrl = coverUrl,
        coverWidth = coverWidth,
        coverHeight = coverHeight,
        publishTime = publishTime,
        updateTime = updateTime,
        collectionUrl = collectionUrl
    )
}


fun CollectionDto.toEntity(): CollectionEntity {
    return CollectionEntity(
        remoteId = id,
        title = name,
        description = description,
        totalPhotos = totalPhotos,
        coverUrl = cover.links.full,
        coverWidth = cover.width,
        coverHeight = cover.height,
        publishTime = IsoToLong(publishedAt),
        updateTime = IsoToLong(updatedAt),
        collectionUrl = links.full
    )
}

fun CollectionDto.toDomain(): Collection {
    return Collection(
        id = id,
        title = name,
        description = description,
        totalPhotos = totalPhotos,
        publishTime = IsoToLong(publishedAt),
        updateTime = IsoToLong(updatedAt),
        coverUrl = cover.links.full,
        coverWidth = cover.width,
        coverHeight = cover.height,
        collectionUrl = links.full
    )
}