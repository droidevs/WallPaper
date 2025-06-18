package io.droidevs.wallpaper.domain.usecases.favorites.collections

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class IsCollectionFavoritedUseCase(
    private val repository: FavoritesRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(collectionId: Long): Flow<Result<Boolean, DataError>> {
        return repository.isFavorited(collectionId.toString(), FavoriteType.Collection)
            .flowOn(dispatchers.io)
    }
}