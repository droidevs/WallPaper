package io.droidevs.wallpaper.ui.model.albums

data class AlbumUi(
    val id: Long,
    val title: String,
    val description: String = "",
    val createdAt : Long = System.currentTimeMillis(),
    val coverImageUrl: String? = null,
    val total : Int = 0,
    val isSelected : Boolean = false
)