package io.droidevs.wallpaper.ui.viewmodels.event


import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.Wallpaper

sealed class AddEvent {

    data class AddWallpaperToAlbum(val wallpaper: Wallpaper, val album: Album) : AddEvent() {
        fun getDetails() = "Adding wallpaper '${wallpaper.name}' to album '${album.title}'"
    }

    data class AddWallpapersToAlbum(val wallpapers: List<Wallpaper>, val album: Album) : AddEvent() {
        fun getDetails() = "Adding ${wallpapers.size} wallpapers to album '${album.title}'"
    }

    data class AddAlbum(val album: Album) : AddEvent() {
        fun getDetails() = "Adding new album '${album.title}'"
    }

    data class AddAlbumWithWallpaper(val album: Album, val wallpaper: Wallpaper) : AddEvent() {
        fun getDetails() = "Creating album '${album.title}' with wallpaper '${wallpaper.name}'"
    }

    data class AddAlbumWithWallpapers(val album: Album, val wallpapers: List<Wallpaper>) : AddEvent() {
        fun getDetails() = "Creating album '${album.title}' with ${wallpapers.size} wallpapers"
    }

    data class AddAlbumWithNewWallpapers(val album: Album, val wallpapersUri: List<String>) : AddEvent() {
        fun getDetails() = "Creating album '${album.title}' with ${wallpapersUri.size} new wallpapers"
    }

    companion object {
        fun fromWallpaper(wallpaper: Wallpaper, album: Album) = AddWallpaperToAlbum(wallpaper, album)
        fun fromWallpapers(wallpapers: List<Wallpaper>, album: Album) = AddWallpapersToAlbum(wallpapers, album)
        fun fromNewAlbum(album: Album) = AddAlbum(album)
        fun fromAlbumWithWallpaper(album: Album, wallpaper: Wallpaper) = AddAlbumWithWallpaper(album, wallpaper)
        fun fromAlbumWithWallpapers(album: Album, wallpapers: List<Wallpaper>) = AddAlbumWithWallpapers(album, wallpapers)
        fun fromNewWallpapers(album: Album, wallpapersUri: List<String>) = AddAlbumWithNewWallpapers(album, wallpapersUri)
    }
}
