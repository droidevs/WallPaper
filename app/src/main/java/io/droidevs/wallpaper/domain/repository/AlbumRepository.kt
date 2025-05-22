package io.droidevs.wallpaper.domain.repository

import io.droidevs.wallpaper.data.model.AlbumEntity
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun addAlbum(album: Album): Result<Long, DatabaseError>

    suspend fun addAlbums(albums: List<Album>) : Result<List<Long>, DatabaseError>

    suspend fun updateAlbum(album: Album) : Result<Int, DatabaseError>


    suspend fun deleteAlbum(album: Album) : Result<Int, DatabaseError>


    suspend fun deleteAllAlbums() : Result<Int, DatabaseError>

    fun getAlbumById(id: Long): Flow<Result<Album, DatabaseError>>

    fun getAllAlbums(): Flow<Result<List<Album>, DatabaseError>>

    fun getAlbumsByGenre(genre: String): Flow<Result<List<Album>, DatabaseError>>

    fun getAlbumsByPage(limit: Int, offset: Int): Flow<Result<List<Album>, DatabaseError>>
}