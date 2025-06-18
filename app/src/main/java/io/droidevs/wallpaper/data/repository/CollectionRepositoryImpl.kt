package io.droidevs.wallpaper.data.repository


import androidx.room.util.query
import io.droidevs.wallpaper.data.local.CollectionEntity
import io.droidevs.wallpaper.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.wallpaper.data.local.dao.CollectionDao
import io.droidevs.wallpaper.data.local.exceptions.DatabaseException
import io.droidevs.wallpaper.data.mappers.toDomain
import io.droidevs.wallpaper.data.network.collection.CollectionApi
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import io.droidevs.wallpaper.domain.model.Collection
import io.droidevs.wallpaper.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.flow
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.map
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao,
    private val collectionApi: CollectionApi,
    private val dispatcher: AppDispatchers
) : CollectionRepository {

    override suspend fun insertCollection(collection: Collection): Result<Long, DataError> {
        return runCatchingDatabaseResult {
            val id = collectionDao.insertCollection(collection.toEntity())
            id
        }
    }

    override suspend fun insertAll(collections: List<Collection>): Result<List<Long>, DataError> {
        return withContext(dispatcher.io) {
            runCatchingDatabaseResult {
                val entities = collections.map { it.toEntity() }
                val result = collectionDao.insertAll(entities)
                result
            }
        }
    }

    override fun getCollectionById(id: Int): Flow<Result<Collection?, DataError>> {
        return flow<Result<Collection?, DataError>> {
            collectionApi.getCollection(id)
        }.flowOn(dispatcher.io)
    }

    override fun getOnlineCollections(
        searchQuery: String?,
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Collection>, DataError>> = flow {

        val collections = searchQuery?.let {
            collectionApi.searchCollection(
                query = searchQuery,
                page = page,
                perPage = pageSize
            ).map {
                it.results
            }
        }?: collectionApi.listCollections(
                page = page,
                perPage = pageSize
            )

        emit(
            collections.map { collections ->
                collections.map {
                    it.toDomain()
                }
            }
        )
    }

    override fun getRelatedCollections(collectionId: Int): Flow<Result<List<Collection>, DataError>> = flow {
        val collections = collectionApi.getRelatedCollections(collectionId)
        emit(
            collections.map { collections ->
                collections.map {
                    it.toDomain()
                }
            }
        )
    }

    override suspend fun updateCollection(collection: Collection): Result<Unit, DataError> {
        return runCatchingDatabaseResult {
            withContext(dispatcher.io){
                collectionDao.updateCollection(collection.toEntity())
            }
        }
    }

    override suspend fun deleteCollection(collection: CollectionEntity): Result<Int, DataError> {
        return runCatchingDatabaseResult {
            withContext(dispatcher.io){
                val deleted = collectionDao.deleteCollection(collection)
                if (deleted > 0) deleted
                else
                    throw DatabaseException.NoElementFound()
            }
        }
    }

    override suspend fun deleteCollectionById(id: Int): Result<Int, DataError> {
        return runCatchingDatabaseResult {
            withContext(dispatcher.io){
                val deleted = collectionDao.deleteCollectionById(id)
                if (deleted > 0) deleted
                else
                    throw DatabaseException.NoElementFound()
            }
        }
    }

    override suspend fun clearAllCollections(): Result<Int, DataError> {
        return runCatchingDatabaseResult {
            val cleared = collectionDao.clearAllCollections()
            cleared
        }
    }
}