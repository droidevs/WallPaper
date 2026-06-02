package io.droidevs.wallpaper.domain.usecases.favorites.photo

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class CountFavoritesPhotosUseCase(
    private val repository: FavoritesRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(): Flow<Result<Int, DataError>> {
        return repository.countByType(FavoriteType.Photo)
            .flowOn(dispatchers.io)
    }
}