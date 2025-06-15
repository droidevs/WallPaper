package io.droidevs.wallpaper.data.repository

import io.droidevs.wallpaper.data.local.AppDatabase
import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.data.local.dao.RemoteKeyDao
import io.droidevs.wallpaper.data.local.dao.TopicDao
import io.droidevs.wallpaper.data.local.exceptions.DatabaseException
import io.droidevs.wallpaper.data.local.exceptions.flowRunCatchingDatabase
import io.droidevs.wallpaper.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.wallpaper.data.mappers.toEntity
import io.droidevs.wallpaper.data.network.dtos.toDomain
import io.droidevs.wallpaper.data.network.topic.TopicApi
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.domain.mappers.toDomain
import io.droidevs.wallpaper.domain.mappers.toEntity
import io.droidevs.wallpaper.domain.model.Topic
import io.droidevs.wallpaper.domain.repository.TopicRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.errors.NetworkError
import io.droidevs.wallpaper.domain.result.map
import io.droidevs.wallpaper.domain.result.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val topicDao: TopicDao,
    private val topicApi: TopicApi,
    private val keyDao : RemoteKeyDao,
    private val database : AppDatabase,
    private val dispatcher: AppDispatchers
) : TopicRepository {

    override suspend fun insertTopic(topic: Topic): Result<Long, DataError> {
        return runCatchingDatabaseResult {
            withContext(dispatcher.io) {
                topicDao.insertTopic(topic.toEntity())
            }
        }
    }

    override suspend fun insertAllTopics(topics: List<Topic>): Result<List<Long>, DataError> {
        return runCatchingDatabaseResult {
            topicDao.insertAll(
                topics.map {
                    it.toEntity()
                }
            )
        }
    }


    override suspend fun clearAllTopics(): Result<Int, DataError> {
        return runCatchingDatabaseResult {
            withContext(dispatcher.io) {
                topicDao.clearAll()
            }
        }
    }

    override fun getAllTopics(): Flow<Result<List<Topic>, DataError>> {
        return flowRunCatchingDatabase {
            topicDao.getAllTopics().map { topics->
                topics.map {
                    it.toDomain()
                }
            }
        }
    }

    override suspend fun getTopicById(id: String): Flow<Result<Topic, DataError>> {
        return flowRunCatchingDatabase {
            topicDao.getTopicById(id)
                .map { topic ->
                    topic?.let { it.toDomain() }?: topicApi.getTopic(id).getOrNull()?.toDomain()
                    ?: throw DatabaseException.NoElementFound()
                }
        }
    }


    override fun getTopicsPaged(page: Int, pageSize: Int): Flow<Result<List<Topic>, DataError>> =
        flowRunCatchingDatabase {
            topicDao.getTopics(page, pageSize)
                .map { topics->
                    topics.map {
                        it.toDomain()
                    }
                }
        }

    override fun getOnlineTopicsPaged(
        page: Int,
        pageSize: Int,
        orderBy: TopicOrderBy
    ): Flow<Result<List<Topic>, NetworkError>> = flow {

        val result = topicApi.listTopics(
            page = page,
            perPage = pageSize,
            orderBy = orderBy.value
        ).map { topics->
            topics.map {
                it.toDomain()
            }
        }

        emit(result)
    }


}