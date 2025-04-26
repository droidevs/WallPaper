package io.droidevs.wallpaper.domain.mappers

import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.domain.model.Topic

// Entity -> Domain
fun TopicEntity.toDomain() = Topic(
    id = remote,
    title = title,
    description = description,
    publishedAt = publishedAt,
    updatedAt = updatedAt,
    startsAt = startsAt,
    endsAt = endsAt,
    totalPhotos = totalPhotos,
    coverPhotoUrl = coverPhotoUrl
)