package io.droidevs.wallpaper.domain.model

data class Collection(
    val id: Int,
    val title: String,
    val description: String,
    val totalPhotos: Int,
    val publishTime: Long,
    val updateTime: Long,
    val coverUrl: String,
    val coverWidth: Int,
    val coverHeight: Int,
    val collectionUrl: String
)