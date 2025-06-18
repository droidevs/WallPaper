package io.droidevs.wallpaper.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.droidevs.wallpaper.data.local.FavoriteEntity
import io.droidevs.wallpaper.domain.model.FavoriteType
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity) : Long

    @Delete
    suspend fun delete(favorite: FavoriteEntity) : Int

    @Query("DELETE FROM favorites WHERE itemId = :itemId AND itemType = :type")
    suspend fun deleteById(itemId: String, type: FavoriteType) : Int

    @Query("SELECT * FROM favorites WHERE itemId = :itemId AND itemType = :type LIMIT 1")
    suspend fun get(itemId: String, type: FavoriteType): Flow<FavoriteEntity?>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE itemId = :itemId AND itemType = :type)")
    suspend fun isFavorited(itemId: String, type: FavoriteType): Flow<Boolean>

    @Query("SELECT * FROM favorites WHERE itemType = :type ORDER BY favoritedAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getByType(type: FavoriteType, limit: Int, offset: Int): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites ORDER BY favoritedAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getAll(limit: Int, offset: Int): Flow<List<FavoriteEntity>>

    @Query("SELECT COUNT(*) FROM favorites WHERE itemType = :type")
    suspend fun countByType(type: FavoriteType): Flow<Int>
}