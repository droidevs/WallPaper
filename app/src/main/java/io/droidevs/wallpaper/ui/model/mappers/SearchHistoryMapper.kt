package io.droidevs.wallpaper.ui.model.mappers

import io.droidevs.wallpaper.domain.model.SearchHistory
import io.droidevs.wallpaper.ui.model.SearchHistoryUi


fun SearchHistory.toUiModel(): SearchHistoryUi{
    return SearchHistoryUi(
        id = id,
        query = query,
        timestamp = timestamp
    )
}