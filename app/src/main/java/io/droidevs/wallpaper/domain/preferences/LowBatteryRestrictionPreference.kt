package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface LowBatteryRestrictionPreference {

    val isLowBatteryRestrictionEnabled: Flow<Result<Boolean, PreferenceError>>

    suspend fun setLowBatteryRestrictionEnabled(enabled: Boolean) : Result<Boolean, PreferenceError>

}