package io.droidevs.wallpaper.domain.usecases.data.albums

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.repository.AlbumRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.withContext


class DeleteAlbumUseCase(
    private val albumRepository: AlbumRepository,
    private val dispatchers: AppDispatchers
) {
    /**
     * Deletes a specific album from the repository.
     *
     * @param album The album to delete.
     * @return A Result containing the number of rows affected (usually 1 on success)
     *         or a DatabaseError on failure.
     */
    suspend operator fun invoke(album: Album): Result<Int, DatabaseError> = withContext(dispatchers.io){
        albumRepository.deleteAlbum(album)
    }
}