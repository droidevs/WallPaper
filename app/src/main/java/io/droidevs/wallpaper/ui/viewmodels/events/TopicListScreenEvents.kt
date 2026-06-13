package io.droidevs.wallpaper.ui.viewmodels.events

import io.droidevs.wallpaper.ui.model.TopicUi
import io.droidevs.wallpaper.ui.model.collections.CollectionUi

// Events
sealed class TopicListScreenEvents {
    data class NavigateToTopicScreen(val collection: TopicUi) : TopicListScreenEvents()

}