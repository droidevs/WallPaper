package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.model.ResizeMethod
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface ResizeMethodPreference {
    suspend fun saveResizeMethod(method: ResizeMethod) : Result<ResizeMethod, PreferenceError>
    val resizedMethod : Flow<Result<ResizeMethod, PreferenceError>>
}
