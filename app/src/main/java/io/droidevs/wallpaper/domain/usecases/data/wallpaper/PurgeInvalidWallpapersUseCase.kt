package io.droidevs.wallpaper.domain.usecases.data.wallpaper

import android.content.Context
import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import io.droidevs.wallpaper.domain.usecases.SuspendingUseCase

class PurgeInvalidWallpapersUseCase(
    private val repository: LocalWallpaperRepository
) : SuspendingUseCase<Context, Unit>() {
    override suspend fun execute(parameters: Context) {
        repository.purgeInvalidWallpapers(parameters)
    }
}