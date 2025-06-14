package io.droidevs.wallpaper.domain.model

data class SearchHistory(
    val id: Long,
    val query: String,
    val timestamp: Long
)