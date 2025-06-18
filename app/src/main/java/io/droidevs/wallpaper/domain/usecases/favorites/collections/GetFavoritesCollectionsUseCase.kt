package io.droidevs.wallpaper.domain.usecases.favorites.collections

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetFavoritesCollectionsUseCase(
    private val repository: FavoritesRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Long>, DataError>> =
        withContext(dispatchers.io) {
            repository.getFavoritesByType(
                type = FavoriteType.Collection,
                page = page,
                pageSize = pageSize
            ).mapResult { favorites ->
                favorites.map {
                    it.itemId.toLong()
                }
            }
        }
}