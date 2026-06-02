package io.droidevs.wallpaper.domain.usecases.preference.resize_method

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.ResizeMethod
import io.droidevs.wallpaper.domain.preferences.ResizeMethodPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.withContext

class SetResizeMethodUseCase(
    private val preference: ResizeMethodPreference,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(method: ResizeMethod): Result<ResizeMethod, PreferenceError> =
        withContext(dispatchers.io) {
            preference.saveResizeMethod(method)
        }
}