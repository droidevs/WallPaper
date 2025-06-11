package io.droidevs.wallpaper.data.model


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "SearchHistory",
    // Create an index to make lookups by query and screenType faster.
    // unique = true ensures we don't store the exact same query for the same screen twice.
    indices = [Index(value = ["query", "screenType"], unique = true)]
)
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val query: String,
    val screenType: SearchScreenType,
    // Store the timestamp of the last search to sort by recency.
    val timestamp: Instant
)