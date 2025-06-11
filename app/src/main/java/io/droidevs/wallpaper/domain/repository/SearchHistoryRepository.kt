package io.droidevs.wallpaper.domain.repository

import io.droidevs.wallpaper.data.model.SearchHistoryEntity
import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    suspend fun addSearchTerm(query: String, screenType: SearchScreenType): Result<Long, DatabaseError>

    fun getSearchHistory(screenType: SearchScreenType): Flow<Result<List<SearchHistoryEntity>, DatabaseError>>

    suspend fun deleteSearchHistoryItem(id: Long) : Result<Int, DatabaseError>

    suspend fun clearSearchHistory(screenType: SearchScreenType) : Result<Int, DatabaseError>
}

