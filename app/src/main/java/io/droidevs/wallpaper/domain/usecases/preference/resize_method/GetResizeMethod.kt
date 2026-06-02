package io.droidevs.wallpaper.domain.usecases.preference.resize_method

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.ResizeMethod
import io.droidevs.wallpaper.domain.preferences.ResizeMethodPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetResizeMethodUseCase(
    private val preference: ResizeMethodPreference,
    private val dispatchers: AppDispatchers
) {
    operator fun invoke(): Flow<Result<ResizeMethod, PreferenceError>> =
        preference.resizedMethod
            .flowOn(dispatchers.io)
}