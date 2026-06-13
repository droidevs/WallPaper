package io.droidevs.wallpaper.ui.viewmodels.state

import io.droidevs.wallpaper.domain.LocalWallpaper

data class LocalWallpaperListScreenState(
    val mode : LoadingMode = LoadingMode.Ide,
    val wallpapers : List<LocalWallpaper> = emptyList(),
    val error : String? = null,
    val endReached : Boolean = false,
    val page  : Int = 0
)