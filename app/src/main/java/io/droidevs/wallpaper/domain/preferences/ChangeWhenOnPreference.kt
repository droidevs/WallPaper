package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface ChangeWhenOnPreference {

    val isChangeWhenOnEnabled: Flow<Result<Boolean, PreferenceError>>

    suspend fun setChangeWhenOnEnabled(enabled: Boolean) : Result<Boolean, PreferenceError>

}