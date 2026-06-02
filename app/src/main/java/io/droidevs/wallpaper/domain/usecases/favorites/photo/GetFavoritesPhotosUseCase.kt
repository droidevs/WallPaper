package io.droidevs.wallpaper.domain.usecases.favorites.photo

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetFavoritesPhotosUseCase(
    private val repository: FavoritesRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int
    ): Flow<Result<List<String>, DataError>> {
        return repository.getFavoritesByType(
            type = FavoriteType.Photo,
            page = page,
            pageSize = pageSize
        ).mapResult{ favorites ->
            favorites.map {
                it.itemId
            }
        }.flowOn(dispatchers.io)
    }
}