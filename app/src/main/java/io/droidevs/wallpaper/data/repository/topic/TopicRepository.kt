package io.droidevs.wallpaper.data.repository.topic

import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.data.pager.impl.TopicsPaginator
import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    suspend fun insertTopic(topic: TopicEntity)
    suspend fun insertAllTopics(topics: List<TopicEntity>)

    suspend fun clearAllTopics()

    fun getAllTopics(): Flow<List<TopicEntity>>
    suspend fun getTopicById(id: String): TopicEntity?
    fun getTopicsPaged(): Flow<List<TopicEntity>>

    fun getTopicsPaginator(
        initialKey: Int = 1,
        pageSize: Int = 20,
        currentSortBy: TopicOrderBy? = null,
        onSuccess: (List<Topic>, Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ): TopicsPaginator

}