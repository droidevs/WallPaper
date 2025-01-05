package io.droidevs.wallpaper.infrastructure.datasource.dao

import androidx.room.*
import io.droidevs.wallpaper.infrastructure.model.AlbumEntity

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)

    @Update
    suspend fun updateAlbum(album: AlbumEntity)

    @Delete
    suspend fun deleteAlbum(album: AlbumEntity)

    @Query("DELETE FROM albums")
    suspend fun deleteAllAlbums()

    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: Long): AlbumEntity?

    @Query("SELECT * FROM albums ORDER BY title ASC")
    suspend fun getAllAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM albums ORDER BY releaseYear DESC LIMIT :limit OFFSET :offset")
    suspend fun getAlbumsByPage(limit: Int, offset: Int): List<AlbumEntity>

    @Query("SELECT * FROM albums WHERE genre = :genre ORDER BY title ASC")
    suspend fun getAlbumsByGenre(genre: String): List<AlbumEntity>
}
