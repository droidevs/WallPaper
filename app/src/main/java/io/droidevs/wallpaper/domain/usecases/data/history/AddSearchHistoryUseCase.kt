package io.droidevs.wallpaper.domain.usecases.data.history

import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.repository.SearchHistoryRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddSearchHistoryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(query: String, screenType: SearchScreenType) : Result<Long, DatabaseError> {
        return withContext(dispatchers.io){
            repository.addSearchTerm(query, screenType)
        }
    }
}