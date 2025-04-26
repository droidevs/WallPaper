package io.droidevs.wallpaper.domain.model

data class Topic(
    val id: String,
    val title: String,
    val description: String?,
    val publishedAt: Long,
    val updatedAt: Long,
    val startsAt: Long?,
    val endsAt: Long?,
    val totalPhotos: Int,
    val coverPhotoUrl: String
)
