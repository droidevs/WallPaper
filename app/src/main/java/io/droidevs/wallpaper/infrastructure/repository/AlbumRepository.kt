package io.droidevs.wallpaper.infrastructure.repository

import io.droidevs.wallpaper.infrastructure.datasource.dao.AlbumDao
import io.droidevs.wallpaper.infrastructure.model.AlbumEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository(private val albumDao: AlbumDao) {

    // Insert a single album
    suspend fun addAlbum(album: AlbumEntity): Long = withContext(Dispatchers.IO) {
        albumDao.insertAlbum(album)
    }

    // Insert multiple albums
    suspend fun addAlbums(albums: List<AlbumEntity>) = withContext(Dispatchers.IO) {
        albumDao.insertAlbums(albums)
    }

    // Update an existing album
    suspend fun updateAlbum(album: AlbumEntity) = withContext(Dispatchers.IO) {
        albumDao.updateAlbum(album)
    }

    // Delete a specific album
    suspend fun deleteAlbum(album: AlbumEntity) = withContext(Dispatchers.IO) {
        albumDao.deleteAlbum(album)
    }

    // Delete all albums
    suspend fun deleteAllAlbums() = withContext(Dispatchers.IO) {
        albumDao.deleteAllAlbums()
    }

    // Get a single album by ID
    suspend fun getAlbumById(id: Long): AlbumEntity? = withContext(Dispatchers.IO) {
        albumDao.getAlbumById(id)
    }

    // Get all albums
    suspend fun getAllAlbums(): List<AlbumEntity> = withContext(Dispatchers.IO) {
        albumDao.getAllAlbums()
    }

    // Get albums by genre
    suspend fun getAlbumsByGenre(genre: String): List<AlbumEntity> = withContext(Dispatchers.IO) {
        albumDao.getAlbumsByGenre(genre)
    }

    // Get paginated albums
    suspend fun getAlbumsByPage(limit: Int, offset: Int): List<AlbumEntity> = withContext(Dispatchers.IO) {
        albumDao.getAlbumsByPage(limit, offset)
    }
}
