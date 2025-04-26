package io.droidevs.wallpaper.ui.viewmodels.actions

import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.ui.model.TopicUiModel

sealed class TopicsAction {
    data class ChangeSortOrder(val sortOrder: TopicOrderBy) : TopicsAction()
    object Refresh : TopicsAction()
    object Retry : TopicsAction()
    data class TopicClicked(val topic: TopicUiModel) : TopicsAction()
}