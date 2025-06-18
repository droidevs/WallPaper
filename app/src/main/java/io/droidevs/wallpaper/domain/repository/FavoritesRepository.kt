package io.droidevs.wallpaper.domain.repository

import io.droidevs.wallpaper.data.local.FavoriteEntity
import io.droidevs.wallpaper.domain.model.Favorite
import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun addFavorite(itemId: Long, type: FavoriteType): Result<Boolean, DataError>
    suspend fun removeFavorite(itemId: Long, type: FavoriteType): Result<Boolean, DataError>
    suspend fun isFavorited(itemId: Long, type: FavoriteType): Flow<Result<Boolean, DataError>>
    suspend fun getFavoritesByType(type: FavoriteType, page: Int, pageSize: Int): Flow<Result<List<Favorite>, DataError>>
    suspend fun getAllFavorites(page: Int, pageSize: Int): Flow<Result<List<FavoriteEntity>, DataError>>
    suspend fun countByType(type: FavoriteType): Flow<Result<Int, DataError>>
}