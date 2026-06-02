package io.droidevs.wallpaper.domain.usecases.data.wallpaper

import io.droidevs.wallpaper.data.pager.impl.LocalWallpapersPaginator
import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import io.droidevs.wallpaper.data.util.SortOrder
import io.droidevs.wallpaper.data.util.SortType
import io.droidevs.wallpaper.data.util.WallpaperSort
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.domain.usecases.SuspendingUseCase

class GetWallpapersPaginatorUseCase(
    private val repository: LocalWallpaperRepository
) : SuspendingUseCase<GetWallpapersPaginatorParams, LocalWallpapersPaginator>() {
    override suspend fun execute(parameters: GetWallpapersPaginatorParams): LocalWallpapersPaginator {
        return repository.getWallpaperPaginator(
            pageSize = parameters.pageSize,
            sort = parameters.sort,
            onLoadUpdated = parameters.onLoadUpdated,
            onError = parameters.onError,
            onSuccess = { data, hasMore ->
                parameters.onSuccess(
                    data,
                    hasMore
                )
            }
        )
    }
}

data class GetWallpapersPaginatorParams(
    val pageSize: Int,
    val sort: WallpaperSort = WallpaperSort(SortType.DATE, SortOrder.DESC),
    val onLoadUpdated: (Boolean) -> Unit,
    val onError: (Throwable) -> Unit,
    val onSuccess: (data: List<LocalWallpaper>, newPage: Int) -> Unit
)