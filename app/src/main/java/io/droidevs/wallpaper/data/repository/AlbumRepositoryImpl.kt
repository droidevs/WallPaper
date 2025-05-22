package io.droidevs.wallpaper.data.repository

import io.droidevs.wallpaper.data.local.dao.AlbumDao
import io.droidevs.wallpaper.data.local.exceptions.DatabaseException
import io.droidevs.wallpaper.data.local.exceptions.flowRunCatchingDatabase
import io.droidevs.wallpaper.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.wallpaper.data.mappers.toDomainModel
import io.droidevs.wallpaper.data.mappers.toEntity
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.repository.AlbumRepository
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import io.droidevs.wallpaper.domain.result.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlbumRepositoryImpl(
    private val albumDao: AlbumDao
) : AlbumRepository {

    // Insert a single album
    override suspend fun addAlbum(album: Album): Result<Long,DatabaseError> =
        runCatchingDatabaseResult {
            albumDao.insertAlbum(album.toEntity())
        }

    // Insert multiple albums
    override suspend fun addAlbums(albums: List<Album>) : Result<List<Long>,DatabaseError> =
        runCatchingDatabaseResult {
            albumDao.insertAlbums(
                albums.map {
                    it.toEntity()
                }
            )
        }

    // Update an existing album
    override suspend fun updateAlbum(album: Album) : Result<Int,DatabaseError> =
        runCatchingDatabaseResult {
            albumDao.updateAlbum(album.toEntity())
        }

    // Delete a specific album
    override suspend fun deleteAlbum(album: Album) : Result<Int,DatabaseError> =
        runCatchingDatabaseResult {
            albumDao.deleteAlbum(album.toEntity())
        }

    // Delete all albums
    override suspend fun deleteAllAlbums() : Result<Int,DatabaseError> =
        runCatchingDatabaseResult {
            albumDao.deleteAllAlbums()
        }

    // Get a single album by ID
    override fun getAlbumById(id: Long): Flow<Result<Album, DatabaseError>> =
        flowRunCatchingDatabase {
            val album = albumDao.getAlbumById(id)
            album.map {
                it ?: throw DatabaseException.NoElementFound()
            }
        }.mapResult {
            it.toDomainModel()
        }

    // Get all albums
    override fun getAllAlbums(): Flow<Result<List<Album>, DatabaseError>> =
        flowRunCatchingDatabase {
            albumDao.getAllAlbums()
        }.mapResult { albums ->
            albums.map {
                it.toDomainModel()
            }
        }

    // Get albums by genre
    override fun getAlbumsByGenre(genre: String): Flow<Result<List<Album>, DatabaseError>> =
        flowRunCatchingDatabase {
            albumDao.getAlbumsByGenre(genre)
        }.mapResult { albums ->
            albums.map {
                it.toDomainModel()
            }
        }

    // Get paginated albums
    override fun getAlbumsByPage(limit: Int, offset: Int): Flow<Result<List<Album>, DatabaseError>> =
        flowRunCatchingDatabase {
            albumDao.getAlbumsByPage(limit, offset)
        }.mapResult { albums ->
            albums.map {
                it.toDomainModel()
            }
        }
}
