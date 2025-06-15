package io.droidevs.wallpaper.ui.model.mappers

import io.droidevs.wallpaper.domain.model.Topic
import io.droidevs.wallpaper.ui.model.TopicUi


// Domain -> UI
fun Topic.toUiModel() = TopicUi(
    id = id,
    title = title,
    description = description,
    publishedAt = publishedAt,
    updatedAt = updatedAt,
    totalPhotos = totalPhotos,
    coverPhotoUrl = coverPhotoUrl
)