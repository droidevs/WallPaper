package io.droidevs.wallpaper.data.repository

import io.droidevs.wallpaper.data.local.FavoriteEntity
import io.droidevs.wallpaper.data.local.dao.FavoritesDao
import io.droidevs.wallpaper.data.local.exceptions.flowRunCatchingDatabase
import io.droidevs.wallpaper.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.wallpaper.domain.mappers.toDomain
import io.droidevs.wallpaper.domain.model.Favorite
import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoritesDao
) : FavoritesRepository {

    override suspend fun addFavorite(itemId: Long, type: FavoriteType): Result<Boolean, DataError> =
        runCatchingDatabaseResult {
            val compositeId = FavoriteEntity.createId(type, itemId)
            if (dao.isFavorited(compositeId, type).first()) {
                false
            } else {
                val index = dao.insert(
                    FavoriteEntity(
                        itemId = compositeId,
                        type = type
                    )
                )
                index != -1L
            }
        }

    override suspend fun removeFavorite(itemId: Long, type: FavoriteType): Result<Boolean, DataError> =
        runCatchingDatabaseResult {
            val compositeId = FavoriteEntity.createId(type, itemId)
            if (dao.isFavorited(compositeId, type).first()) {
                dao.deleteById(compositeId, type)
                true
            } else {
                false
            }
        }

    override suspend fun isFavorited(itemId: Long, type: FavoriteType): Flow<Result<Boolean, DataError>> =
        flowRunCatchingDatabase {
            dao.isFavorited(FavoriteEntity.createId(type, itemId), type)
        }

    override suspend fun getFavoritesByType(
        type: FavoriteType,
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Favorite>, DataError>> =
        flowRunCatchingDatabase {
            dao.getByType(type, limit = pageSize, offset = page * pageSize)
                .map { favorites ->
                    favorites.map {
                        it.toDomain()
                    }
                }
        }

    override suspend fun getAllFavorites(page: Int, pageSize: Int): Flow<Result<List<FavoriteEntity>, DataError>> =
        flowRunCatchingDatabase {
            dao.getAll(limit = pageSize, offset = page * pageSize)
        }

    override suspend fun countByType(type: FavoriteType): Flow<Result<Int, DataError>> =
        flowRunCatchingDatabase {
            dao.countByType(type)
        }
}