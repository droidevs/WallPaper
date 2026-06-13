package io.droidevs.wallpaper.domain.usecases.preference.orientation_lock

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.preferences.LandscapeOrientationLockPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.withContext

class SetLandscapeOrientationLockUseCase(
    private val preference: LandscapeOrientationLockPreference,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> =
        withContext(dispatchers.io) {
            preference.setLandscapeOrientationLockEnabled(enabled)
        }
}