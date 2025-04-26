package io.droidevs.wallpaper.data.datastore

import io.droidevs.wallpaper.models.AutoWallpaperSettings
import io.droidevs.wallpaper.models.WallpaperAlbumSetup
import io.droidevs.wallpaper.models.Screen
import kotlinx.coroutines.flow.Flow


interface AutoWallpaperSettingsPreference {
    suspend fun getSettings(): AutoWallpaperSettings
    fun observeSettings(): Flow<AutoWallpaperSettings>

    suspend fun saveHomeScreen(setup: WallpaperAlbumSetup?)
    suspend fun saveLockScreen(setup: WallpaperAlbumSetup?)
    suspend fun setAutoMode(target : Screen, enabled: Boolean)

    suspend fun clear()
}
