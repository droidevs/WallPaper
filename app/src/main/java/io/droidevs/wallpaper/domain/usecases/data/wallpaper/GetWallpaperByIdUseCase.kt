package io.droidevs.wallpaper.domain.usecases.data.wallpaper

import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.domain.usecases.FlowUseCase
import kotlinx.coroutines.flow.Flow

class GetWallpaperByIdUseCase(
    private val repository: LocalWallpaperRepository
) : FlowUseCase<String, LocalWallpaper>() {
    override fun execute(parameters: String): Flow<LocalWallpaper?> {
        return repository.getWallpaperById(parameters)
    }
}