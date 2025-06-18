package io.droidevs.wallpaper.domain.repository

import io.droidevs.wallpaper.data.local.CollectionEntity
import io.droidevs.wallpaper.domain.model.Collection
import kotlinx.coroutines.flow.Flow
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError

interface CollectionRepository {
    // Insert operations
    suspend fun insertCollection(collection: Collection): Result<Long, DataError>
    suspend fun insertAll(collections: List<Collection>): Result<List<Long>, DataError> // Returns count

    // Query operations
    fun getCollectionById(id: Int): Flow<Result<Collection?, DataError>>

    fun getOnlineCollections(
        searchQuery: String? = null,
        page: Int,
        pageSize: Int
    ) : Flow<Result<List<Collection>, DataError>>


    fun getRelatedCollections(
        collectionId: Int,
    ) : Flow<Result<List<Collection>, DataError>>



    // Update/Delete operations
    suspend fun updateCollection(collection: Collection): Result<Unit, DataError>
    suspend fun deleteCollectionById(id: Int): Result<Int, DataError> // Returns deleted count

    suspend fun deleteCollection(collection: CollectionEntity): Result<Int, DataError>
    suspend fun clearAllCollections(): Result<Int, DataError> // Returns cleared count

}