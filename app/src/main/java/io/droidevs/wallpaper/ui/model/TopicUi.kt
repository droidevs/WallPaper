package io.droidevs.wallpaper.ui.model

data class TopicUi(
    val id: String,
    val title: String,
    val description: String?,
    val publishedAt: Long,
    val updatedAt: Long,
    val totalPhotos: Int,
    val coverPhotoUrl: String
)
