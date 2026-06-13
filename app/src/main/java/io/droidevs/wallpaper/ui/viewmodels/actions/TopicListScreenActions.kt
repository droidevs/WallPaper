package io.droidevs.wallpaper.ui.viewmodels.actions

import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.ui.model.TopicUi

sealed class TopicListScreenAction {
    data class ChangeSortOrder(val sortOrder: TopicOrderBy) : TopicListScreenAction()
    object Refresh : TopicListScreenAction()
    object LoadingMoreTopics : TopicListScreenAction()

    data class TopicClicked(val topic: TopicUi) : TopicListScreenAction()
}