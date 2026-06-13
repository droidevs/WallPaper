package io.droidevs.wallpaper.domain.usecases.preference.change_when

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.preferences.ChangeWhenOffPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetChangeWhenOffStatusUseCase(
    private val preference: ChangeWhenOffPreference,
    private val dispatchers: AppDispatchers
) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> =
        preference.isChangeWhenOffEnabled
            .flowOn(dispatchers.io)
}