package io.droidevs.wallpaper.ui.viewmodels.events

import io.droidevs.wallpaper.ui.model.collections.CollectionUi

sealed class CollectionListScreenEvent {
    data class NavigateToCollectionScreen(val collection: CollectionUi) : CollectionListScreenEvent()

}