package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.model.AutoWallpaperSettings
import io.droidevs.wallpaper.domain.model.WallpaperAlbumSetup
import io.droidevs.wallpaper.domain.model.Screen
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow


interface AutoWallpaperSettingsPreference {

    val settings : Flow<Result<AutoWallpaperSettings, PreferenceError>>

    suspend fun saveHomeScreen(setup: WallpaperAlbumSetup?) : Result<AutoWallpaperSettings, PreferenceError>
    suspend fun saveLockScreen(setup: WallpaperAlbumSetup?) : Result<AutoWallpaperSettings, PreferenceError>
    suspend fun setAutoMode(target : Screen, enabled: Boolean) : Result<AutoWallpaperSettings, PreferenceError>

    suspend fun clear() : Result<AutoWallpaperSettings, PreferenceError>
}
