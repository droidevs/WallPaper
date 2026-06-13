package io.droidevs.wallpaper.ui.viewmodels.events

sealed interface LoadEvent {
    data object Refresh : LoadEvent
    data object LoadMore : LoadEvent
}

sealed interface SelectEvent {
    data class Select(val id: String) : SelectEvent
    data class Deselect(val id: String) : SelectEvent
}

sealed interface DeleteEvent {
    data object DeleteAlbum : DeleteEvent
    data class DeleteWallpapers(val ids: List<String>) : DeleteEvent
}
