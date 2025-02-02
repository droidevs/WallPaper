package io.droidevs.wallpaper.ui.viewmodels.event

sealed class LoadEvent {

    object LoadMore : LoadEvent()

    object Refresh : LoadEvent()

}