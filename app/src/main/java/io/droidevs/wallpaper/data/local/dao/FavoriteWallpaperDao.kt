package io.droidevs.wallpaper.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.droidevs.wallpaper.data.local.FavoriteWallpaperDetail
import io.droidevs.wallpaper.data.local.FavoriteWallpaperEntity

@Dao
interface FavoriteWallpaperDao {
    // Get all favorites with full details
    @Transaction
    @Query("""
        SELECT * FROM favorite_wallpapers 
        ORDER BY custom_order DESC, added_at DESC
    """)
    suspend fun getAllFavoriteDetails(): List<FavoriteWallpaperDetail>

    // Get single favorite with details
    @Transaction
    @Query("""
        SELECT * FROM favorite_wallpapers 
        WHERE wallpaper_id = :wallpaperId
    """)
    suspend fun getFavoriteDetail(wallpaperId: String): FavoriteWallpaperDetail?

    // Basic favorite operations
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: FavoriteWallpaperEntity): Long

    @Delete
    suspend fun removeFavorite(favorite: FavoriteWallpaperEntity)

    // Partial updates
    @Query("""
        UPDATE favorite_wallpapers 
        SET custom_order = :newOrder 
        WHERE wallpaper_id = :wallpaperId
    """)
    suspend fun updateFavoriteOrder(wallpaperId: String, newOrder: Int)

    // Status check
    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM favorite_wallpapers 
            WHERE wallpaper_id = :wallpaperId 
            LIMIT 1
        )
    """)
    suspend fun isFavorite(wallpaperId: String): Boolean
}