package io.droidevs.wallpaper.domain.usecases.data.history

import io.droidevs.wallpaper.data.local.AppDatabase
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.repository.SearchHistoryRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Use case to delete a single history item
class DeleteSearchHistoryItemUseCase @Inject constructor(
    private val repository: SearchHistoryRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(id: Long): Result<Int, DatabaseError> {
        return withContext(dispatchers.io){
            repository.deleteSearchHistoryItem(id)
        }
    }
}