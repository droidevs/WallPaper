package io.droidevs.wallpaper.ui.viewmodels.event

import io.droidevs.wallpaper.domain.Wallpaper


sealed class SortEvent{

    data object SortAlphabetically : SortEvent()
    data object SortAlphabeticallyReverse : SortEvent()
    data object SortByLastModified : SortEvent()

    data object SortByLastModifiedReverse : SortEvent()

}