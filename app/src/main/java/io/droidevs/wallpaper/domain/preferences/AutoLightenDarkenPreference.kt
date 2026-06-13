package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow


interface AutoLightenDarkenPreference {

    val isEnabled : Flow<Result<Boolean, PreferenceError>>

    suspend fun  setEnabled(enabled: Boolean) : Result<Boolean, PreferenceError>

}