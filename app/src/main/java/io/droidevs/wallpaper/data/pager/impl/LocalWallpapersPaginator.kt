package io.droidevs.wallpaper.data.pager.impl

import io.droidevs.wallpaper.data.model.LocalWallpaperEntity
import io.droidevs.wallpaper.domain.pager.DefaultPaginator
import io.droidevs.wallpaper.domain.result.Result as DomainResult
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.errors.DatabaseError

class LocalWallpapersPaginator(
    initialKey: Int,
    onLoadUpdated: (Boolean) -> Unit,
    onRequest: suspend (Int) -> Result<List<LocalWallpaperEntity>>,
    getNextKey: suspend (key: Int, data: List<LocalWallpaperEntity>) -> Int,
    onError: suspend (Throwable) -> Unit,
    onSuccess: (List<LocalWallpaperEntity>, Int) -> Unit
) : DefaultPaginator<Int, LocalWallpaperEntity>(
    initialKey = initialKey,
    onLoadUpdated = onLoadUpdated,
    onRequest = { key ->
        onRequest(key).fold(
            onSuccess = { DomainResult.Success(it) },
            onFailure = { DomainResult.Failure(DatabaseError.UnknownError(it)) }
        )
    },
    getNextKey = getNextKey,
    onError = { error ->
        onError(Exception(error.toString()))
    },
    onSuccess = onSuccess
)