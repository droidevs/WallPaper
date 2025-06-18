package io.droidevs.wallpaper.domain.usecases.data.collection

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.Collection
import io.droidevs.wallpaper.domain.repository.CollectionRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetCachedCollectionsUseCase(
    val repository: CollectionRepository,
    val dispatchers: AppDispatchers
) {

    operator fun invoke(
        page: Int = 1,
        pageSize: Int = 20
    ) : Flow<Result<List<Collection>, DataError>>  {
        return repository.getCollections(
            page = page,
            pageSize = pageSize
        ).flowOn(dispatchers.io)
    }
}