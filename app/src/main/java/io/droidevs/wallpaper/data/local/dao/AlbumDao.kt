package io.droidevs.wallpaper.data.local.dao

import androidx.room.*
import io.droidevs.wallpaper.data.model.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>) : List<Long>

    @Update
    suspend fun updateAlbum(album: AlbumEntity) : Int

    @Delete
    suspend fun deleteAlbum(album: AlbumEntity) : Int

    @Query("DELETE FROM albums")
    suspend fun deleteAllAlbums() : Int

    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: Long): Flow<AlbumEntity?>

    @Query("SELECT * FROM albums ORDER BY title ASC")
    suspend fun getAllAlbums(): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM albums ORDER BY releaseYear DESC LIMIT :limit OFFSET :offset")
    suspend fun getAlbumsByPage(limit: Int, offset: Int): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM albums WHERE genre = :genre ORDER BY title ASC")
    suspend fun getAlbumsByGenre(genre: String): Flow<List<AlbumEntity>>
}
