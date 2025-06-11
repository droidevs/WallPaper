package io.droidevs.wallpaper.data.repository

import io.droidevs.wallpaper.data.local.dao.SearchHistoryDao
import io.droidevs.wallpaper.data.local.exceptions.flowRunCatchingDatabase
import io.droidevs.wallpaper.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.wallpaper.data.model.SearchHistoryEntity
import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.domain.repository.SearchHistoryRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val dao: SearchHistoryDao
) : SearchHistoryRepository {

    override suspend fun addSearchTerm(query: String, screenType: SearchScreenType): Result<Long, DatabaseError> =
        runCatchingDatabaseResult {
            if (query.isBlank()) return@runCatchingDatabaseResult 0L

            val entity = SearchHistoryEntity(
                query = query.trim(),
                screenType = screenType,
                timestamp = Instant.now()
            )
            dao.upsert(entity)
        }

    override fun getSearchHistory(screenType: SearchScreenType): Flow<Result<List<SearchHistoryEntity>, DatabaseError>> =
        flowRunCatchingDatabase {
            dao.getRecentSearches(screenType)
        }

    override suspend fun deleteSearchHistoryItem(id: Long): Result<Int, DatabaseError> = runCatchingDatabaseResult {
        dao.deleteById(id)
    }

    override suspend fun clearSearchHistory(screenType: SearchScreenType): Result<Int, DatabaseError> = runCatchingDatabaseResult {
        dao.clearHistoryForScreen(screenType)
    }
}