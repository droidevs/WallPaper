package io.droidevs.wallpaper.domain.usecases.preference.low_battery

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.preferences.LowBatteryRestrictionPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.withContext


class SetLowBatteryRestrictionUseCase(
    private val preference: LowBatteryRestrictionPreference,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> =
        withContext(dispatchers.io) {
            preference.setLowBatteryRestrictionEnabled(enabled)
        }
}