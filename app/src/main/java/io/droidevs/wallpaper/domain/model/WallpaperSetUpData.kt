package io.droidevs.wallpaper.domain.model

data class WallpaperSetUpData(
    val image: ImageData,
    val screen : Screen,
    val dimens: ScreenDimens,
    val effect: Effect? = null
)

data class ScreenDimens(
    val width: Int,
    val height: Int
)