package io.droidevs.wallpaper.data.local.dao

import io.droidevs.wallpaper.data.model.SearchHistoryEntity
import io.droidevs.wallpaper.data.model.SearchScreenType

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    /**
     * Inserts a search history entry. If an entry with the same query and screenType
     * already exists, it updates its timestamp. This is ideal for moving a
     * recently searched item to the top of the history list.
     */
    @Upsert
    suspend fun upsert(searchHistory: SearchHistoryEntity) : Long

    /**
     * Retrieves the most recent search history for a specific screen, ordered by the
     * last time they were searched.
     * @param screenType The screen to get history for.
     * @param limit The maximum number of history items to return.
     * @return A Flow emitting the list of recent search queries.
     */
    @Query("""
        SELECT * FROM SearchHistory
        WHERE screenType = :screenType
        ORDER BY timestamp DESC
        LIMIT :limit
    """)
    fun getRecentSearches(screenType: SearchScreenType, limit: Int = 10): Flow<List<SearchHistoryEntity>>

    /**
     * Deletes a specific search history item by its ID.
     */
    @Query("DELETE FROM SearchHistory WHERE id = :id")
    suspend fun deleteById(id: Long)  : Int

    /**
     * Clears all search history for a specific screen.
     */
    @Query("DELETE FROM SearchHistory WHERE screenType = :screenType")
    suspend fun clearHistoryForScreen(screenType: SearchScreenType) : Int
}