package io.droidevs.wallpaper.domain.usecases.preference.photo_filter

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.PhotoFilter
import io.droidevs.wallpaper.domain.preferences.PhotoFilterPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetPhotoFilterUseCase(
    private val preference: PhotoFilterPreference,
    private val dispatchers: AppDispatchers
) {
    operator fun invoke(): Flow<Result<PhotoFilter, PreferenceError>> =
        preference.photoFilter.flowOn(dispatchers.io)
}