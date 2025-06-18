package io.droidevs.wallpaper.ui.viewmodels.actions

import io.droidevs.wallpaper.ui.model.TopicUi

sealed class CollectionListScreenAction {

    object Refresh : CollectionListScreenAction()
    object LoadingMore : CollectionListScreenAction()

    data class CollectionClicked(val topic: TopicUi) : CollectionListScreenAction()
}