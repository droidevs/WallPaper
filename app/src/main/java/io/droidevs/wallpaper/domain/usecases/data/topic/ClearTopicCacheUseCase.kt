package io.droidevs.wallpaper.domain.usecases.data.topic

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.repository.TopicRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.withContext

class ClearTopicCacheUseCase(
    val repo : TopicRepository,
    val dispatchers : AppDispatchers
) {

    suspend operator fun invoke() : Result<Int, DataError> = withContext(dispatchers.io) {
        repo.clearAllTopics()
    }
}