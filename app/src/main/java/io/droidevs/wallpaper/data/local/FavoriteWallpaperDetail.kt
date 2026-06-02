package io.droidevs.wallpaper.data.local

import androidx.room.Embedded
import androidx.room.Relation
import io.droidevs.wallpaper.data.model.LocalWallpaperEntity

data class FavoriteWallpaperDetail(
    @Embedded
    val favoriteInfo: FavoriteWallpaperEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "wallpaper_id"
    )
    val wallpaper: LocalWallpaperEntity
)