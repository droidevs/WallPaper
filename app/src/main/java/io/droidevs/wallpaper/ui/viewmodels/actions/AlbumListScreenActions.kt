package io.droidevs.wallpaper.ui.viewmodels.actions

import io.droidevs.wallpaper.ui.model.albums.AlbumUi

sealed interface AlbumListScreenAction {

    data class SearchQueryChanged(val query: String): AlbumListScreenAction
    data class SelectAlbum(val album: AlbumUi): AlbumListScreenAction

    data class DeselectAlbum(val album: AlbumUi): AlbumListScreenAction

    data object DeselectAll: AlbumListScreenAction

    data class ClickAlbum(val album: AlbumUi) : AlbumListScreenAction

    data class EditAlbum(val album: AlbumUi) : AlbumListScreenAction


    data class SearchActiveChanged(val isActive: Boolean) : AlbumListScreenAction
    data class DeleteAlbum(val album: AlbumUi) : AlbumListScreenAction
    data object OnSearchAction : AlbumListScreenAction

    data object DeleteAllSelected : AlbumListScreenAction

    data object LoadMoreAlbums : AlbumListScreenAction


    data object RefreshAlbums : AlbumListScreenAction


}