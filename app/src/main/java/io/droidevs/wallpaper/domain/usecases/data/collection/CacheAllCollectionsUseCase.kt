package io.droidevs.wallpaper.domain.usecases.data.collection

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.Collection
import io.droidevs.wallpaper.domain.repository.CollectionRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.withContext

class CacheAllCollectionsUseCase(
    private val repository: CollectionRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(collections: List<Collection>): Result<List<Long>, DataError> =
        withContext(dispatchers.io) {
            repository.insertAll(collections)
        }
}