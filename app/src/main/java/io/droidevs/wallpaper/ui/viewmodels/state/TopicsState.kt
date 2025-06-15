package io.droidevs.wallpaper.ui.viewmodels.state

import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.ui.model.TopicUi

// State
data class TopicsState(
    val topics : List<TopicUi> = emptyList<TopicUi>(),
    val currentSortOrder: TopicOrderBy = TopicOrderBy.LATEST,
    val state: LoadingMode = LoadingMode.Ide,
    val error: Error? = null,
    val page : Int = 0,
    val pageSize : Int = 20,
    val endReached: Boolean = false,
)