package io.droidevs.wallpaper.domain.repository

import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.domain.model.Topic
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.NetworkError

interface TopicRepository {
    suspend fun insertTopic(topic: Topic) : Result<Long, DataError>

    suspend fun insertAllTopics(topics: List<Topic>) :Result<List<Long>, DataError>

    suspend fun clearAllTopics() : Result<Int, DataError>

    fun getAllTopics(): Flow<Result<List<Topic>, DataError>>
    suspend fun getTopicById(id: String): Flow<Result<Topic, DataError>>

    fun getTopicsPaged(page: Int, limit: Int): Flow<Result<List<Topic>, DataError>>

    fun getOnlineTopicsPaged(
        page: Int,
        pageSize: Int,
        orderBy: TopicOrderBy
    ) : Flow<Result<List<Topic>, NetworkError>>
}