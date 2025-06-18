package io.droidevs.wallpaper.domain.usecases.favorites.topics

import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow

class CountFavoritesTopicsUseCase(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(): Flow<Result<Int, DataError>> {
        return repository.countByType(FavoriteType.Topic)
    }

}