package io.droidevs.wallpaper.domain

data class Album(
    val id: Long,
    val title: String,
    val artist: String,
    val releaseYear: Int,
    val genre: String,
    val coverImageUrl: String? = null,
    val total : Int = 0
)
