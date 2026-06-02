package io.droidevs.wallpaper.domain.model


data class RemoteWallpaper(
    val id: Long,
    val remoteId : String,
    val createdAt: Long,
    val updatedAt: Long,
    val width: Int,
    val height: Int,
    val color: String,
    val blurHash: String,
    val downloads: Int,
    val likes: Int,
    val description: String?,
    val url: String?,
    val thumbUrl : String?,
    val isFavorite: Boolean = false
) {
    val aspectRatio: Float get() = width.toFloat() / height.toFloat()
}
