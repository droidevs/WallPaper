package io.droidevs.wallpaper.ui.viewmodels.events

import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.ui.model.SearchHistoryUi


sealed class SearchScreenEvent {

    data object NavigateBack : SearchScreenEvent()

    data class Search(val query: String, val screenType: SearchScreenType) : SearchScreenEvent()

}