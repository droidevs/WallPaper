package io.droidevs.wallpaper.domain.repository

import io.droidevs.wallpaper.data.local.FavoriteWallpaperDetail
import io.droidevs.wallpaper.data.local.FavoriteWallpaperEntity
import io.droidevs.wallpaper.data.local.dao.FavoriteWallpaperDao

import javax.inject.Inject

class FavoriteWallpaperRepository @Inject constructor(
    private val favoriteDao: FavoriteWallpaperDao
) {
    // Get all favorites with wallpaper details
    suspend fun getAllFavorites(): List<FavoriteWallpaperDetail> {
        return favoriteDao.getAllFavoriteDetails()
    }

    // Add to favorites
    suspend fun addFavorite(wallpaperId: String) {
        // Add to favorites table
        favoriteDao.insertFavorite(
            FavoriteWallpaperEntity(wallpaperId = wallpaperId)
        )
    }

    // Remove from favorites
    suspend fun removeFavorite(wallpaperId: String) {
        // Remove from favorites table
        favoriteDao.getFavoriteDetail(wallpaperId)?.favoriteInfo?.let {
            favoriteDao.removeFavorite(it)
        }
    }

    // Update specific fields
    suspend fun updateOrder(wallpaperId: String, newOrder: Int) {
        favoriteDao.updateFavoriteOrder(wallpaperId, newOrder)
    }


    // Check status
    suspend fun isFavorite(wallpaperId: String): Boolean {
        return favoriteDao.isFavorite(wallpaperId)
    }

    // Toggle favorite status
    suspend fun toggleFavorite(wallpaperId: String){
        if (isFavorite(wallpaperId)) {
            removeFavorite(wallpaperId)
        } else {
            addFavorite(wallpaperId)
        }
    }
}