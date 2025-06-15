package io.droidevs.wallpaper.domain.usecases.data.topic

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.repository.TopicRepository
import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.domain.model.Topic
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopicsUseCase @Inject constructor(
    private val repository: TopicRepository,
    private val dispatchers: AppDispatchers
) {
    operator fun invoke(
        page: Int,
        pageSize: Int,
    ): Flow<Result<List<Topic>,DataError>> {
        return repository.getTopicsPaged(
            page = page,
            limit = pageSize
        ).flowOn(dispatchers.io)
    }

}
