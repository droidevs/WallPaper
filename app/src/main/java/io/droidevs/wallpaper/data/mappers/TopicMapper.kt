package io.droidevs.wallpaper.data.mappers

import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.data.network.dtos.TopicDto
import io.droidevs.wallpaper.domain.model.Topic
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// String -> Long?
@OptIn(ExperimentalTime::class)
fun String.toMillisOrNull(): Long? = runCatching {
    Instant.parse(this).toEpochMilliseconds()
}.getOrNull()

// DTO -> Entity
fun TopicDto.toEntity() = TopicEntity(
    remote = id,
    slug = slug,
    title = title,
    description = description,
    publishedAt = publishedAt.toMillisOrNull() ?: 0L,
    updatedAt = updatedAt.toMillisOrNull() ?: 0L,
    startsAt = startsAt?.toMillisOrNull(),
    endsAt = endsAt?.toMillisOrNull(),
    totalPhotos = totalPhotos,
    coverPhotoUrl = coverPhoto.urls.full // adjust based on DTO structure
)
