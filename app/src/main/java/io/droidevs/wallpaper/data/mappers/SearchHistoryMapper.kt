package io.droidevs.wallpaper.data.mappers

import io.droidevs.wallpaper.data.model.SearchHistoryEntity
import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.domain.model.SearchHistory
import java.time.Instant


fun SearchHistoryEntity.toDomain(): SearchHistory{
    return SearchHistory(
        id = id,
        query = searchQuery,
        timestamp = timestamp.toEpochMilli()
    )
}

fun SearchHistory.toEntity(screenType: SearchScreenType): SearchHistoryEntity{
    return SearchHistoryEntity(
        id = id,
        searchQuery = query,
        screenType = screenType,
        timestamp = Instant.ofEpochMilli(timestamp)
    )
}