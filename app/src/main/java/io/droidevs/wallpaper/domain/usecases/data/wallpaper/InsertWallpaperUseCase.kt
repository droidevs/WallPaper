package io.droidevs.wallpaper.domain.usecases.data.wallpaper

import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import io.droidevs.wallpaper.domain.LocalWallpaper

class InsertWallpaperUseCase(
    private val repository: LocalWallpaperRepository
) {
    suspend operator fun invoke(wallpaper: LocalWallpaper){
        repository.insertWallpaper(wallpaper)
    }
}