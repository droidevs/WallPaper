package io.droidevs.wallpaper.ui.viewmodels.events

// Events
sealed class TopicListScreenEvents {
    data class NavigateToTopicScreen(val topicId: String) : TopicListScreenEvents()

    data class ShowError(val message: String) : TopicListScreenEvents()
}