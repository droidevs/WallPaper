package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface ChangeWhenOffPreference {

    val isChangeWhenOffEnabled: Flow<Result<Boolean, PreferenceError>>

    suspend fun setChangeWhenOffEnabled(enabled: Boolean) : Result<Boolean, PreferenceError>

}