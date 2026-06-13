package io.droidevs.wallpaper.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "photos")
data class RemoteWallpaperEntity(
    @PrimaryKey
    val id: Long = 0,
    val remoteId : String,
    val createdAt: Long,
    val updatedAt: Long,
    val width: Int,
    val height: Int,
    val color: String,
    val blurHash: String,
    val downloads: Int,
    val likes: Int,
    val description: String?,
    val url: String? = null,
    val thumbUrl: String? = null,
)