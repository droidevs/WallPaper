package io.droidevs.wallpaper.domain.usecases.data.wallpaper

import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.domain.usecases.FlowUseCase
import kotlinx.coroutines.flow.Flow

class GetAllWallpapersUseCase(
    private val repository: LocalWallpaperRepository
) : FlowUseCase<Unit, List<LocalWallpaper>>() {
    override fun execute(parameters: Unit): Flow<List<LocalWallpaper>> {
        return repository.getAllWallpapers()
    }
}