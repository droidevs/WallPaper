package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow


interface PortraitOrientationLockPreference {

    val isPortraitOrientationLockEnabled: Flow<Result<Boolean, PreferenceError>>

    suspend fun setPortraitOrientationLockEnabled(enabled: Boolean): Result<Boolean, PreferenceError>

}