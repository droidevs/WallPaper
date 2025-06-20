package io.droidevs.wallpaper.ui.model

// A new data class to group filters under a single category.
data class FilterCategory(
    val title: String,
    val filters: List<FilterItem>
)