package io.droidevs.wallpaper.data.repository.topic

import io.droidevs.wallpaper.data.local.AppDatabase
import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.data.local.dao.RemoteKeyDao
import io.droidevs.wallpaper.data.local.dao.TopicDao
import io.droidevs.wallpaper.data.mappers.toEntity
import io.droidevs.wallpaper.data.network.topic.TopicApi
import io.droidevs.wallpaper.data.network.result.map
import io.droidevs.wallpaper.data.pager.impl.TopicsPaginator
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.domain.mappers.toDomain
import io.droidevs.wallpaper.domain.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val topicDao: TopicDao,
    private val topicApi: TopicApi,
    private val keyDao : RemoteKeyDao,
    private val database : AppDatabase,
    private val dispatcher: AppDispatchers
) : TopicRepository {

    override suspend fun insertTopic(topic: TopicEntity) {
        withContext(dispatcher.io) {
            topicDao.insertTopic(topic)
        }
    }

    override suspend fun insertAllTopics(topics: List<TopicEntity>) {
        withContext(dispatcher.io) {
            topicDao.insertAll(topics)
        }
    }

    override suspend fun clearAllTopics() {
        withContext(dispatcher.io) {
            topicDao.clearAll()
        }
    }

    override fun getAllTopics(): Flow<List<TopicEntity>> {
        return topicDao.getAllTopics().flowOn(dispatcher.io)
    }

    override suspend fun getTopicById(id: String): TopicEntity? {
        return withContext(dispatcher.io) {
            topicDao.getTopicById(id)
        }
    }

    override fun getTopicsPaged(): Flow<List<TopicEntity>> {
        TODO("Not yet implemented")
    }

    override fun getTopicsPaginator(
        initialKey: Int,
        pageSize: Int,
        currentSortBy: TopicOrderBy?,
        onSuccess: (List<Topic>, Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ): TopicsPaginator {
        return TopicsPaginator(
            pageSize = pageSize,
            loadItems = { page, size ->
                topicApi.listTopics(
                    page = page,
                    perPage = pageSize,
                    orderBy = currentSortBy?.value).map { topics ->
                    topics.map {
                        it.toEntity()
                    }
                }
            },
            getItemsFromCache = { page, limit ->
                topicDao.getTopics(page, limit).first()
            },
            cacheItems = { items ->
                topicDao.insertTopics(items)
            },
            clearCache = {
                topicDao.clearAll()
            },
            onLoadUpdated = { /* Handle loading state if needed */ },
            onError = { error ->
                onError(error)
            },
            onSuccess = { items, hasMore ->
                onSuccess(
                    items.map {
                        it.toDomain()
                    }
                    , hasMore)
            }
        )
    }

}