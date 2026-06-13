package io.droidevs.wallpaper.domain.usecases.preference.change_when

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.preferences.ChangeWhenOnPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.withContext

class SetChangeWhenOnStatusUseCase(
    private val preference: ChangeWhenOnPreference,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> =
        withContext(dispatchers.io) {
            preference.setChangeWhenOnEnabled(enabled)
        }
}