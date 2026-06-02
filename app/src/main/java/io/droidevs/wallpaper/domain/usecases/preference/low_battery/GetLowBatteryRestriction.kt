package io.droidevs.wallpaper.domain.usecases.preference.low_battery

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.preferences.LowBatteryRestrictionPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetLowBatteryRestrictionStatusUseCase(
    private val preference: LowBatteryRestrictionPreference,
    private val dispatchers: AppDispatchers
) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> =
        preference.isLowBatteryRestrictionEnabled
            .flowOn(dispatchers.io)
}