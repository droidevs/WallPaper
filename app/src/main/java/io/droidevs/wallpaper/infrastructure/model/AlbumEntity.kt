package io.droidevs.wallpaper.infrastructure.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val artist: String = "",
    val releaseYear: Int = 0,
    val genre: String = "",
    val coverImageUrl: String? = null, // Optional for album cover image
    val totalWallpapers : Int = 0
)
