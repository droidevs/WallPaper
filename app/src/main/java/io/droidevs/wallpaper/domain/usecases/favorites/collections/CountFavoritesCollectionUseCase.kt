package io.droidevs.wallpaper.domain.usecases.favorites.collections

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CountFavoritesCollectionUseCase(
    private val repository: FavoritesRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(): Flow<Result<Int, DataError>> =
        withContext(dispatchers.io) {
            repository.countByType(FavoriteType.Collection)
        }
}