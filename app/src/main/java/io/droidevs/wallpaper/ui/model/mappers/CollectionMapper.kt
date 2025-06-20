package io.droidevs.wallpaper.ui.model.mappers

import io.droidevs.wallpaper.domain.model.Collection
import io.droidevs.wallpaper.ui.model.collections.CollectionUi


fun Collection.toUiModel() : CollectionUi {
    return CollectionUi(
        id = id,
        title = title,
        description = description,
        totalPhotos = totalPhotos,
        coverUrl = coverUrl,
        coverWidth = coverWidth,
        coverHeight = coverHeight,
        publishTime = publishTime,
        updateTime = updateTime,
        selected = false,
        collectionUrl = collectionUrl
    )
}


fun CollectionUi.toDomainModel() : Collection {
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