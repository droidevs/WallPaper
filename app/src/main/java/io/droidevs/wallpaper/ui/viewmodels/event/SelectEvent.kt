package io.droidevs.wallpaper.ui.viewmodels.event


sealed class SelectEvent {

    data class Select(val wallpaper : String) : SelectEvent()

    data class Deselect(val wallpaper: String) : SelectEvent()

}