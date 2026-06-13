package io.droidevs.wallpaper.domain.usecases.data.wallpaper

import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.domain.usecases.SuspendingUseCase

class UpdateWallpaperUseCase(
    private val repository: LocalWallpaperRepository
) : SuspendingUseCase<LocalWallpaper, Unit>() {
    override suspend fun execute(parameters: LocalWallpaper) {
        repository.updateWallpaper(parameters)
    }
}