package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import io.droidevs.wallpaper.data.datastore.delegate.ProtoDelegateEach
import io.droidevs.wallpaper.data.datastore.delegate.ProtoPreferenceDelegate
import io.droidevs.wallpaper.data.datastore.delegate.ProtoWriteDelegate
import io.droidevs.wallpaper.data.datastore.delegate.ProtoWriteDelegateImpl
import io.droidevs.wallpaper.domain.preferences.AutoWallpaperSettingsPreference
import io.droidevs.wallpaper.domain.model.AutoWallpaperSettings
import io.droidevs.wallpaper.domain.model.Effect
import io.droidevs.wallpaper.domain.model.WallpaperAlbumSetup
import io.droidevs.wallpaper.domain.model.Screen
import io.droidevs.wallpaper.domain.model.WallpaperEffects
import io.droidevs.wallpaper.domain.model.WallpaperSetUpData
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AutoWallpaperSettingsDataStore(
    private val dataStore: DataStore<AutoWallpaperSettings>
) : AutoWallpaperSettingsPreference, ProtoWriteDelegate<AutoWallpaperSettings> by ProtoWriteDelegateImpl<AutoWallpaperSettings>(dataStore) {

    val settingDelegate by lazy {
        ProtoPreferenceDelegate(
            dataStore = dataStore,
            defaultValue = AutoWallpaperSettings()
        )
    }


    private suspend fun updateAutoMode(
        screen: Screen,
        enabled: Boolean
    ): Result<AutoWallpaperSettings, PreferenceError> {
        return set(
            value = enabled,
            update = { root, enabled ->
                when (screen) {
                    Screen.HOME -> root.copy(homeScreen = root.homeScreen?.copy(autoModeEnabled = enabled))
                    Screen.LOCK -> root.copy(lockScreen = root.lockScreen?.copy(autoModeEnabled = enabled))
                    Screen.BOTH -> root.copy(
                        homeScreen = root.homeScreen?.copy(autoModeEnabled = enabled),
                        lockScreen = root.lockScreen?.copy(autoModeEnabled = enabled)
                    )

                    Screen.NONE -> root
                }
            }
        )
    }

    private suspend fun updateSetUp(
        screen: Screen,
        setup: WallpaperAlbumSetup?
    ) : Result<AutoWallpaperSettings, PreferenceError> {
        return set(
            value = setup,
            update = { root, setup ->
                when (screen) {
                    Screen.HOME -> root.copy(homeScreen = setup)
                    Screen.LOCK -> root.copy(homeScreen = setup)
                    Screen.BOTH -> root.copy(
                        homeScreen = setup,
                        lockScreen = setup)

                    Screen.NONE -> root
                }
            }
        )
    }

    private suspend fun updateAlbumValue(
        screen: Screen,
        albumId: String,
    ): Result<AutoWallpaperSettings, PreferenceError> {
        return set(
            value = albumId,
            update = { root, album ->
                when (screen) {
                    Screen.HOME -> root.copy(homeScreen = root.homeScreen?.copy(albumId = albumId))
                    Screen.LOCK -> root.copy(homeScreen = root.lockScreen?.copy(albumId = albumId))
                    Screen.BOTH -> root.copy(
                        homeScreen = root.homeScreen?.copy(albumId = albumId),
                        lockScreen = root.lockScreen?.copy(albumId = albumId
                    ))

                    Screen.NONE -> root
                }
            }
        )
    }

    private suspend fun updateIntervalValue(
        screen: Screen,
        value: Int,
    ): Result<AutoWallpaperSettings, PreferenceError> {
        return set(
            value = value,
            update = { root, interval ->
                when (screen) {
                    Screen.HOME -> root.copy(homeScreen = root.homeScreen?.copy(intervalMinutes = interval))
                    Screen.LOCK -> root.copy(lockScreen = root.lockScreen?.copy(intervalMinutes = interval))
                    Screen.BOTH -> root.copy(
                        homeScreen = root.homeScreen?.copy(intervalMinutes = interval),
                        lockScreen = root.lockScreen?.copy(intervalMinutes = interval)
                    )
                    Screen.NONE -> root
                }
            }
        )
    }

    override val settings : Flow<Result<AutoWallpaperSettings, PreferenceError>>
        get() {
            return settingDelegate.flow
        }

    override suspend fun saveHomeScreen(setup: WallpaperAlbumSetup?): Result<AutoWallpaperSettings, PreferenceError> {
        return updateSetUp(Screen.HOME, setup)
    }

    override suspend fun saveLockScreen(setup: WallpaperAlbumSetup?): Result<AutoWallpaperSettings, PreferenceError> {
        return updateSetUp(Screen.LOCK, setup)
    }

    override suspend fun setAutoMode(target: Screen, enabled: Boolean): Result<AutoWallpaperSettings, PreferenceError> {
        return updateAutoMode(
            screen = target,
            enabled = enabled
        )
    }

    override suspend fun clear(): Result<AutoWallpaperSettings, PreferenceError> {
        return settingDelegate.set(AutoWallpaperSettings())
    }
}
