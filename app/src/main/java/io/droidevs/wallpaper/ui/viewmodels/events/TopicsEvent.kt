package io.droidevs.wallpaper.ui.viewmodels.events

// Events
sealed class TopicsEvent {
    data class NavigateToTopicDetail(val topicId: String) : TopicsEvent()
    data class ShowError(val message: String) : TopicsEvent()
}