package io.droidevs.wallpaper.domain.usecases.favorites.topics

import io.droidevs.wallpaper.data.local.FavoriteEntity
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.FavoriteType
import io.droidevs.wallpaper.domain.repository.FavoritesRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetFavoritesTopicsUseCase(
    private val repository: FavoritesRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Pair<String, Long>>, DataError>> {
        return repository.getFavoritesByType(
            type = FavoriteType.Topic,
            page = page,
            pageSize = pageSize
        ).mapResult { favorites ->
            favorites.map {
                it.itemId to it.favoritedAt
            }
        }.flowOn(dispatchers.io)
    }
}