package io.droidevs.wallpaper.domain.usecases.data.wallpaper

import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import io.droidevs.wallpaper.data.util.SortOrder
import io.droidevs.wallpaper.data.util.SortType
import io.droidevs.wallpaper.data.util.WallpaperSort
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.domain.usecases.FlowUseCase
import kotlinx.coroutines.flow.Flow

class GetWallpapersPageUseCase(
    private val repository: LocalWallpaperRepository
) : FlowUseCase<GetWallpapersPageParams, List<LocalWallpaper>>() {
    override fun execute(parameters: GetWallpapersPageParams): Flow<List<LocalWallpaper>> {
        return repository.getWallpapersPage(parameters.page, parameters.pageSize, parameters.sort)
    }
}

data class GetWallpapersPageParams(
    val page: Int,
    val pageSize: Int,
    val sort: WallpaperSort = WallpaperSort(SortType.DATE, SortOrder.DESC)
)