package io.droidevs.wallpaper.ui.viewmodels.state

import io.droidevs.wallpaper.ui.model.albums.AlbumUi

data class AlbumListScreenState(
    val albums : List<AlbumUi>,
    val state: LoadingMode,
    val endReached : Boolean = false,
    val page  : Int = 0,
    val error : Error? = null,
)