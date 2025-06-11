package io.droidevs.wallpaper.domain.usecases.data.albums

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.repository.AlbumRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetAllAlbumsUseCase (
    private val albumRepository: AlbumRepository,
    private val dispatchers: AppDispatchers
){

    suspend fun invoke() : Flow<Result<List<Album>, DatabaseError>> {
        return albumRepository.getAllAlbums()
            .flowOn(dispatchers.io)
    }
}