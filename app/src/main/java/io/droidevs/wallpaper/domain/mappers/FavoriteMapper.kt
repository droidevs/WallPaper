package io.droidevs.wallpaper.domain.mappers

import io.droidevs.wallpaper.data.local.FavoriteEntity
import io.droidevs.wallpaper.domain.model.Favorite
import io.droidevs.wallpaper.domain.model.FavoriteType


fun Favorite.toEntity() : FavoriteEntity {
    return FavoriteEntity(
        itemId = itemId,
        type = itemType,
        favoritedAt = favoritedAt
    )
}

fun FavoriteEntity.toDomain() : Favorite {
    return Favorite(
        itemId = itemId,
        itemType = type,
        favoritedAt = favoritedAt
    )
}