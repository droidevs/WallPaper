package io.droidevs.wallpaper.domain.usecases.preference.change_when

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.preferences.ChangeWhenOnPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class GetChangeWhenOnStatusUseCase(
    private val preference: ChangeWhenOnPreference,
    private val dispatchers : AppDispatchers
) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> =
        preference.isChangeWhenOnEnabled
            .flowOn(dispatchers.io)
}