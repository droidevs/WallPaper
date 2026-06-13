package io.droidevs.wallpaper.ui.model.collections

data class CollectionUi(
    val id: Int,
    val title: String,
    val description: String,
    val totalPhotos: Int,
    val publishTime: Long,
    val updateTime: Long,
    val coverUrl: String,
    val coverWidth: Int,
    val coverHeight: Int,
    val selected: Boolean = false,
    val collectionUrl: String
)