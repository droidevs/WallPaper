package io.droidevs.wallpaper.data.pager.impl

import io.droidevs.wallpaper.data.model.LocalWallpaperEntity
import io.droidevs.wallpaper.domain.pager.DefaultPaginator

class LocalWallpapersPaginator(initialKey: Int,
                               onLoadUpdated: (Boolean) -> Unit,
                               onRequest: suspend (Int) -> Result<List<LocalWallpaperEntity>>,
                               getNextKey: suspend (key: Int, data: List<LocalWallpaperEntity>) -> Int,
                               onError: suspend (Throwable) -> Unit,
                               onSuccess: (List<LocalWallpaperEntity>, Int) -> Unit
) : DefaultPaginator<Int, LocalWallpaperEntity>(initialKey, onLoadUpdated, onRequest, getNextKey, onError,
    onSuccess
)