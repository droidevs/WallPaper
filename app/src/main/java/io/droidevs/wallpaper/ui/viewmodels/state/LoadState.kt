package io.droidevs.wallpaper.ui.viewmodels.state

import io.droidevs.wallpaper.domain.Wallpaper

data class LoadState(
    val mode : LoadingMode = LoadingMode.Ide,
    val wallpapers : List<Wallpaper> = emptyList(),
    val error : String? = null,
    val endReached : Boolean = false,
    val page  : Int = 0
)

enum class LoadingMode {
    Append,
    Refresh,
    Ide
}