package io.droidevs.wallpaper.domain.pager

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError

class SimpleDefaultPaginator<Item>(initialKey: Int,
                                   onLoadUpdated: (Boolean) -> Unit,
                                   onRequest: suspend (Int) -> Result<List<Item>, DataError>,
                                   onError: suspend (DataError) -> Unit,
                                   onSuccess: (List<Item>, Int) -> Unit
) : DefaultPaginator<Int, Item>(
    initialKey = initialKey,
    onLoadUpdated = onLoadUpdated,
    onRequest = onRequest,
    getNextKey = { key, _ ->
        key + 1
    },
    onError = onError,
    onSuccess = onSuccess
) {
}