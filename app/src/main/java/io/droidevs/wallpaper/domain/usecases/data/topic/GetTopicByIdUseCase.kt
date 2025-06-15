package io.droidevs.wallpaper.domain.usecases.data.topic

import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.Topic
import io.droidevs.wallpaper.domain.repository.TopicRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTopicByIdUseCase @Inject constructor(
    private val repository: TopicRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(id: String): Flow<Result<Topic, DataError>> {
        return repository.getTopicById(id)
            .flowOn(dispatchers.io)
    }
}