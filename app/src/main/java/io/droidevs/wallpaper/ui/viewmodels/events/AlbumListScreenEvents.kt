package io.droidevs.wallpaper.ui.viewmodels.events

sealed interface AlbumListScreenEvent {

    data object AlbumDeletedSuccessfully : AlbumListScreenEvent

    data object AlbumDeleteFailed : AlbumListScreenEvent


}