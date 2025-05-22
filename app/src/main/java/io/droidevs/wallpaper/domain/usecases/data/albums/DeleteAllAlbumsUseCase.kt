package io.droidevs.wallpaper.domain.usecases.data.albums

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.repository.AlbumRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.withContext


class DeleteAllAlbumsUseCase(
    private val albumRepository: AlbumRepository,
    private val dispatchers: AppDispatchers
) {
    /**
     * Deletes all albums from the repository.
     *
     * @return A Result containing the number of rows affected
     *         or a DatabaseError on failure.
     */
    suspend operator fun invoke(): Result<Int, DatabaseError> {
        return withContext(dispatchers.io){
            albumRepository.deleteAllAlbums()
        }
    }
}