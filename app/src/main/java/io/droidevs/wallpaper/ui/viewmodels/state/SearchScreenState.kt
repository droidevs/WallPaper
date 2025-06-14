package io.droidevs.wallpaper.ui.viewmodels.state

import androidx.compose.runtime.Stable
import io.droidevs.wallpaper.ui.model.SearchHistoryUi


@Stable
data class SearchScreenState(
    val query: String = "",
    val suggestions: List<SearchHistoryUi> = emptyList<SearchHistoryUi>(),
    val recentSuggestions: List<SearchHistoryUi> = emptyList<SearchHistoryUi>(),
    val state: LoadingMode = LoadingMode.Ide,
    val endReached : Boolean = false,
    val page  : Int = 0,
    val error : Error? = null,
)