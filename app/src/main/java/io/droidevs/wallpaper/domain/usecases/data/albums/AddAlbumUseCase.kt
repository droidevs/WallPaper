package io.droidevs.wallpaper.domain.usecases.data.albums

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.repository.AlbumRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.withContext


class AddAlbumUseCase(
    private val albumRepository: AlbumRepository,
    private val dispatchers: AppDispatchers
) {
    /**
     * Adds a list of new albums to the repository.
     *
     * @param albums The list of albums to add.
     * @return A Result containing a list of IDs of the newly added albums on success,
     *         or a DatabaseError on failure.
     */
    suspend operator fun invoke(album: Album): Result<Long, DatabaseError> = withContext(dispatchers.io) {
        albumRepository.addAlbum(album)
    }
}