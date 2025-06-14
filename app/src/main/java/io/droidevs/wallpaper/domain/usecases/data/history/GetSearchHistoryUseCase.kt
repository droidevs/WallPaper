package io.droidevs.wallpaper.domain.usecases.data.history


import io.droidevs.wallpaper.data.local.AppDatabase
import io.droidevs.wallpaper.data.model.SearchHistoryEntity
import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.SearchHistory
import io.droidevs.wallpaper.domain.repository.SearchHistoryRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

// Use case to get the list of recent searches
class GetSearchHistoryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository,
    private val dispatchers: AppDispatchers
) {
    operator fun invoke(screenType: SearchScreenType): Flow<Result<List<SearchHistory>, DatabaseError>> {
        return repository.getSearchHistory(screenType)
            .flowOn(dispatchers.io)
    }

    operator fun invoke(
        query: String,
        screenType: SearchScreenType,
        page: Int,
        pageSize: Int
    ): Flow<Result<List<SearchHistory>, DatabaseError>> {
        return repository.getSearchHistory(
            query = query,
            screenType = screenType,
            page = page,
            pageSize = pageSize
        ).flowOn(dispatchers.io)
    }
}