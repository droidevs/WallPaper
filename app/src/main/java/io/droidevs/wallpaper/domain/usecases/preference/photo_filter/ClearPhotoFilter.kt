package io.droidevs.wallpaper.domain.usecases.preference.photo_filter

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.PhotoFilter
import io.droidevs.wallpaper.domain.preferences.PhotoFilterPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.withContext

class ClearPhotoFilterUseCase(
    private val preference: PhotoFilterPreference,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(): Result<PhotoFilter, PreferenceError> =
        withContext(dispatchers.io) {
            preference.clearPhotoFilter()
        }
}