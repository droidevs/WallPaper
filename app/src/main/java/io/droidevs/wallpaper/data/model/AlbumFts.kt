package io.droidevs.wallpaper.data.model

import androidx.room.Entity
import androidx.room.Fts4
import io.droidevs.wallpaper.domain.Album

@Fts4(contentEntity = Album::class)
@Entity(tableName = "AlbumFts")
data class AlbumFts(
    // The columns here MUST match the column names in the 'Album' entity
    val title: String,
    val artist: String,
    val genre: String
    // We omit 'id' and 'year' because we don't need to search them via FTS.
)