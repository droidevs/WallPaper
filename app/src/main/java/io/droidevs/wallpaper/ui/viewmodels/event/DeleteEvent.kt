package io.droidevs.wallpaper.ui.viewmodels.event

sealed class DeleteEvent {

    object DeleteAlbum : DeleteEvent()

    object DeleteAll : DeleteEvent()

    class DeleteWallpaper(id : String) : DeleteEvent()

    class DeleteWallpapers(ids : List<String>) : DeleteEvent()

}