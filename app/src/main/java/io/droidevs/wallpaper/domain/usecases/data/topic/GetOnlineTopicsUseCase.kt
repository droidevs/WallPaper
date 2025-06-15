package io.droidevs.wallpaper.domain.usecases.data.topic

import io.droidevs.wallpaper.data.network.dtos.TopicDto
import io.droidevs.wallpaper.data.network.topic.TopicApi
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.domain.model.Topic
import io.droidevs.wallpaper.domain.repository.TopicRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetOnlineTopicsUseCase(
    private val repo : TopicRepository,
    private val dispatchers: AppDispatchers
) {

    fun invoke(
        page: Int,
        pageSize: Int,
        order: TopicOrderBy,
    ) : Flow<Result<List<Topic>, DataError>>{
        return repo.getOnlineTopicsPaged(
            page = page,
            pageSize = pageSize,
            orderBy = order
        ).flowOn(dispatchers.io)
    }
}