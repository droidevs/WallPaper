package io.droidevs.wallpaper.domain.usecases.data.collection

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.Collection
import io.droidevs.wallpaper.domain.repository.CollectionRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.ui.nav.roots.AppDestinations
import kotlinx.coroutines.withContext

class InsertCollectionUseCase(
    private val repository: CollectionRepository,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(collection: Collection): Result<Long, DataError> = withContext(dispatchers.io) {
        repository.insertCollection(collection)
    }
}