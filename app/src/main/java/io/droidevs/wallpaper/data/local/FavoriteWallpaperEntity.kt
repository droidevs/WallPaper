package io.droidevs.wallpaper.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import io.droidevs.wallpaper.data.model.LocalWallpaperEntity

@Entity(
    tableName = "favorite_wallpapers",
    foreignKeys = [
        ForeignKey(
            entity = LocalWallpaperEntity::class,
            parentColumns = ["wallpaper_id"],
            childColumns = ["wallpaper_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("wallpaper_id", unique = true)]
)
data class FavoriteWallpaperEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favorite_id")
    val id: Long = 0,

    @ColumnInfo(name = "wallpaper_id")
    val wallpaperId: String,

    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "custom_order")
    val customOrder: Int = 0
)