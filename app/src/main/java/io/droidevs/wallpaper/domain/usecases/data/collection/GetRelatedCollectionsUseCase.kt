package io.droidevs.wallpaper.domain.usecases.data.collection

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.Collection
import io.droidevs.wallpaper.domain.repository.CollectionRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRelatedCollectionsUseCase(
    private val repository: CollectionRepository,
    private val dispatchers: AppDispatchers
) {

    operator fun invoke(collectionId: Int) : Flow<Result<List<Collection>, DataError>> {
        return repository.getRelatedCollections(collectionId).flowOn(dispatchers.io)
    }
}