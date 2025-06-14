package io.droidevs.wallpaper.ui.viewmodels.actions

import io.droidevs.wallpaper.ui.model.SearchHistoryUi

sealed class SearchScreenAction {

    data object ClearSearch : SearchScreenAction()

    data class DeleteSuggestion(val suggestion: SearchHistoryUi) : SearchScreenAction()

    data class PickSuggestion(val suggestion: SearchHistoryUi) : SearchScreenAction()

    data object Search : SearchScreenAction()

    data class UpdateQuery(val query: String) : SearchScreenAction()

    data object OnBackPressed : SearchScreenAction()

}