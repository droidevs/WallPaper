package io.droidevs.wallpaper.infrastructure.pager

import io.droidevs.wallpaper.domain.Wallpaper

class WallpapersPaginator(initialKey: Int,
                          onLoadUpdated: (Boolean) -> Unit,
                          onRequest: suspend (Int) -> Result<List<Wallpaper>>,
                          getNextKey: suspend (List<Wallpaper>) -> Int,
                          onError: suspend (Throwable) -> Unit,
                          onSuccess: (List<Wallpaper>, Int) -> Unit
) : DefaultPaginator<Int, Wallpaper>(initialKey, onLoadUpdated, onRequest, getNextKey, onError,
    onSuccess
)