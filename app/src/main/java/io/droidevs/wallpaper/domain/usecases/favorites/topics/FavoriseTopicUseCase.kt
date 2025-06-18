package io.droidevs.wallpaper.domain.usecases.favorites.topics

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.withContext

class FavoriseTopicUseCase(
    private val repository: FavoritesRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(topicId: String): Result<Boolean, DataError> =
        withContext(dispatchers.io) {
            repository.addFavorite(topicId, FavoriteType.Topic)
        }
}