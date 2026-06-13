package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface LandscapeOrientationLockPreference {

    val isLandscapeOrientationLockEnabled: Flow<Result<Boolean, PreferenceError>>

    suspend fun setLandscapeOrientationLockEnabled(enabled: Boolean) : Result<Boolean, PreferenceError>
}