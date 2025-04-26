package io.droidevs.wallpaper.domain.usecases

import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.data.pager.impl.TopicsPaginator
import io.droidevs.wallpaper.data.repository.topic.TopicRepository
import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.domain.model.Topic
import javax.inject.Inject

class GetTopicsUseCase @Inject constructor(
    private val repository: TopicRepository
) {
    operator fun invoke(
        sortOrder : TopicOrderBy = TopicOrderBy.LATEST,
        onSuccess: (topics : List<Topic>, hasMore : Boolean) -> Unit,
        onError: (Throwable) -> Unit): TopicsPaginator {
        return repository.getTopicsPaginator(
            initialKey = 1,
            pageSize = 10,
            currentSortBy = sortOrder,
            onSuccess = onSuccess,
            onError = onError
        )
    }

}
