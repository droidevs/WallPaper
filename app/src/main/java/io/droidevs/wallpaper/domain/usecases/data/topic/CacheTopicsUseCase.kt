package io.droidevs.wallpaper.domain.usecases.data.topic

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.Topic
import io.droidevs.wallpaper.domain.repository.TopicRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CacheTopicsUseCase(
    val repo : TopicRepository,
    val dispatchers: AppDispatchers
) {

    suspend operator fun invoke(
        topics: List<Topic>
    ) : Result<List<Long>, DataError> = withContext(dispatchers.io) {
        repo.insertAllTopics(topics)
    }
}