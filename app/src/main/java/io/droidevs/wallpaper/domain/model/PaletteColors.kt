package io.droidevs.wallpaper.domain.model

data class PaletteColors(
    val dominantColor: Int,
    val vibrantColor: Int?,
    val mutedColor: Int?,
    val darkVibrantColor: Int?,
    val lightVibrantColor: Int?
)