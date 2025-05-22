package io.droidevs.wallpaper.ui.viewmodels.actions

import io.droidevs.wallpaper.ui.model.albums.AlbumUi

sealed interface AlbumListScreenAction {

    data class SelectAlbum(val album: AlbumUi): AlbumListScreenAction

    data class DeselectAlbum(val album: AlbumUi): AlbumListScreenAction

    data object DeselectAll: AlbumListScreenAction

    data class ClickAlbum(val album: AlbumUi) : AlbumListScreenAction
}