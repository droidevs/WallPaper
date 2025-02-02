package io.droidevs.wallpaper.ui.viewmodels.event

import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.Wallpaper

sealed class MoveEvent {



    data class MoveWallpaperToNewAlbum(val wallpaper: Wallpaper, val albumName: String) : MoveEvent() {
        fun getDetails() = "Moving wallpaper '${wallpaper.name}' to new album '$albumName'"
    }

    data class MoveWallpapersToNewAlbum(val wallpaper: List<Wallpaper>, val albumName : String) : MoveEvent() {
        fun getDetails() = "Moving ${wallpaper.size} wallpapers to new album '$albumName'"
    }

    data class MoveWallpaperToAlbum(val wallpaper: Wallpaper, val album: Album) : MoveEvent() {
        fun getDetails() = "Moving wallpaper '${wallpaper.name}' to album '${album.title}'"
    }

    data class MoveWallpapersToAlbum(val wallpapers: List<Wallpaper>, val album: Album) : MoveEvent() {
        fun getDetails() = "Moving ${wallpapers.size} wallpapers to album '${album.title}'"
    }
}