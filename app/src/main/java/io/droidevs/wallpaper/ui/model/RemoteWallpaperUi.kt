package io.droidevs.wallpaper.ui.model


data class RemoteWallpaperUi(
    val id: Long,
    val remoteId: String,
    val date : Long,
    val dimensionsText: String,
    val color: String,
    val blurHash: String,
    val likesText: String,
    val description: String?,
    val url: String?,
    val thumbUrl: String?,
    val aspectRatio: Float,
    val isFavorite: Boolean = false,
    val isSelected: Boolean = false
) {
    companion object {
        val EMPTY = RemoteWallpaperUi(
            id = -1,
            remoteId = "",
            date = -1,
            dimensionsText = "",
            color = "",
            blurHash = "",
            likesText = "",
            description = null,
            url = "",
            thumbUrl = "",
            aspectRatio = 1f,
            isFavorite = false
        )
    }
}