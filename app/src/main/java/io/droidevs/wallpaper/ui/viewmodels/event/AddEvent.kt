package io.droidevs.wallpaper.ui.viewmodels.event


import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.Wallpaper

sealed class AddEvent {

    data class AddAlbum(val album: Album) : AddEvent() {
        fun getDetails() = "Adding new album '${album.title}'"
    }

    data class AddAlbumWithWallpaper(val album: Album, val wallpaperUri: String) : AddEvent() {
        fun getDetails() = "Creating album '${album.title}' with wallpaper uri '${wallpaperUri}'"
    }

    data class AddAlbumWithWallpapers(val album: Album, val wallpapersUri: List<String>) : AddEvent() {
        fun getDetails() = "Creating album '${album.title}' with ${wallpapersUri.size} new wallpapers"
    }

    companion object {
        fun fromNewAlbum(album: Album) = AddAlbum(album)
        fun fromAlbumWithWallpaper(album: Album, wallpaper: String) = AddAlbumWithWallpaper(album, wallpaper)
        fun fromAlbumWithWallpapers(album: Album, wallpapers: List<String>) = AddAlbumWithWallpapers(album, wallpapers)

    }
}
