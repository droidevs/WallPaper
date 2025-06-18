package io.droidevs.wallpaper.domain.model

data class Favorite(
    val itemId: String,
    val itemType: FavoriteType,
    val favoritedAt: Long
)