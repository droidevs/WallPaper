package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import io.droidevs.wallpaper.models.AutoWallpaperSettings
import io.droidevs.wallpaper.models.WallpaperAlbumSetup
import io.droidevs.wallpaper.models.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AutoWallpaperSettingsDataStore(
    private val dataStore: DataStore<AutoWallpaperSettings>
) : AutoWallpaperSettingsPreference {

    override suspend fun getSettings(): AutoWallpaperSettings {
        return dataStore.data.first()
    }

    override fun observeSettings(): Flow<AutoWallpaperSettings> {
        return dataStore.data
    }

    override suspend fun saveHomeScreen(setup: WallpaperAlbumSetup?) {
        dataStore.updateData { current ->
            current.copy(homeScreen = setup)
        }
    }

    override suspend fun saveLockScreen(setup: WallpaperAlbumSetup?) {
        dataStore.updateData { current ->
            current.copy(lockScreen = setup)
        }
    }

    override suspend fun setAutoMode(target: Screen, enabled: Boolean) {
        dataStore.updateData { current ->
            when(target){
                Screen.HOME ->
                    current.copy(homeScreen = current.homeScreen?.copy(autoModeEnabled = enabled))
                Screen.LOCK ->
                    current.copy(lockScreen = current.lockScreen?.copy(autoModeEnabled = enabled))
                Screen.BOTH -> {
                    current.copy(
                        homeScreen = current.homeScreen?.copy(autoModeEnabled = enabled),
                        lockScreen = current.lockScreen?.copy(autoModeEnabled = enabled)
                    )
                }

                Screen.NONE -> { current }
            }
        }
    }

    override suspend fun clear() {
        dataStore.updateData { AutoWallpaperSettings() }
    }
}
