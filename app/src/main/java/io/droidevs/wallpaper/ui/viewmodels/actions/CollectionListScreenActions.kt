package io.droidevs.wallpaper.ui.viewmodels.actions

import io.droidevs.wallpaper.ui.model.TopicUi
import io.droidevs.wallpaper.ui.model.collections.CollectionUi

sealed class CollectionListScreenAction {

    object Refresh : CollectionListScreenAction()

    object RefreshFavored : CollectionListScreenAction()
    object LoadingMore : CollectionListScreenAction()

    object LoadingMoreFavored : CollectionListScreenAction()

    data class Search(val query: String) : CollectionListScreenAction()

    data class SearchFavored(val query: String) : CollectionListScreenAction()

    data class Share(val collection: CollectionUi) : CollectionListScreenAction()

    data class CollectionClicked(val collection: CollectionUi) : CollectionListScreenAction()
}