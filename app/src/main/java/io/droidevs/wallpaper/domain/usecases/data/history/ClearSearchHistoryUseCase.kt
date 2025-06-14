package io.droidevs.wallpaper.domain.usecases.data.history

import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.repository.SearchHistoryRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClearSearchHistoryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(screenType: SearchScreenType) : Result<Int, DatabaseError> {
        return withContext(dispatchers.io){
            repository.clearSearchHistory(screenType)
        }
    }

    suspend fun invoke(): Result<Int, DatabaseError> {
        return withContext(dispatchers.io){
            repository.clearSearchHistory(SearchScreenType.All)
        }
    }
}