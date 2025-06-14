package io.droidevs.wallpaper.ui.viewmodels.events

sealed interface AlbumListScreenEvent {

    data object AlbumDeletedSuccessfully : AlbumListScreenEvent

    data object AlbumDeleteFailed : AlbumListScreenEvent

    data class NavigateToAlbumDetail(val albumId: Long) : AlbumListScreenEvent

    data class NavigateToAlbumEdit(val albumId: Long) : AlbumListScreenEvent

    data class NavigateToSearchScreen(val query: String): AlbumListScreenEvent

}