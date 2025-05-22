package io.droidevs.wallpaper.domain.usecases.data.albums

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.repository.AlbumRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class GetAlbumByIdUseCase(
    private val albumRepository: AlbumRepository,
    private val dispatchers: AppDispatchers
) {
    /**
     * Retrieves a single album by its ID as a Flow.
     * The Flow will emit a new Result<Album, DatabaseError> whenever the underlying data changes.
     *
     * @param id The ID of the album to retrieve.
     * @return A Flow emitting a Result containing the Album on success (or if not found,
     *         the error within Result will indicate DatabaseException.NoElementFound),
     *         or a DatabaseError for other database issues.
     */
    operator fun invoke(id: Long): Flow<Result<Album, DatabaseError>> {
        // The repository's flowOn(dispatchers.io) handles the execution context
        // for the upstream (database query).
        return albumRepository.getAlbumById(id)
            .flowOn(dispatchers.io)
        // If you had transformations here before returning the flow from the repository,
        // and those transformations were CPU intensive, you might use:
        // .flowOn(dispatchers.default)
        // But for simple pass-through, it's not needed.
    }
}