package io.droidevs.wallpaper.domain.usecases.data.collection

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.Collection
import io.droidevs.wallpaper.domain.repository.CollectionRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetCollectionByIdUseCase(
    private val repository: CollectionRepository,
    private val dispatchers: AppDispatchers
) {
    operator fun invoke(id: Long): Flow<Result<Collection?, DataError>> {
        return repository.getCollectionById(id).flowOn(
            dispatchers.io
        )
    }
}