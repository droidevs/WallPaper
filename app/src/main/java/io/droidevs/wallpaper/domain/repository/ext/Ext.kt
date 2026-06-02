package io.droidevs.wallpaper.domain.repository.ext

import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.onSuccess
import io.droidevs.wallpaper.domain.result.onSuccessSuspendWithResult
import io.droidevs.wallpaper.domain.result.onSuccessWithResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

suspend fun FavoritesRepository.toggleFavorite(
    itemId: String,
    type: FavoriteType
): Result<Boolean, DataError> {
    val isFavorited = isFavorited(itemId, type).first()
    return isFavorited.onSuccessSuspendWithResult {
        if (it) {
            removeFavorite(itemId, type)
        } else {
            addFavorite(itemId, type)
        }
    }
}