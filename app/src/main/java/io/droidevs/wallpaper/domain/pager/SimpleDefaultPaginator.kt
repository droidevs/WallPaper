package io.droidevs.wallpaper.domain.pager

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError

class SimpleDefaultPaginator<Item>(initialKey: Int,
                                   onLoadUpdated: (Boolean) -> Unit,
                                   onRequest: suspend (Int) -> Result<List<Item>, DataError>,
                                   getNextKey: suspend (Int, List<Item>) -> Int,
                                   onError: suspend (DataError) -> Unit,
                                   onSuccess: (List<Item>, Int) -> Unit
) : DefaultPaginator<Int, Item>(
    initialKey = initialKey,
    onLoadUpdated = onLoadUpdated,
    onRequest = onRequest,
    getNextKey = getNextKey,
    onError = onError,
    onSuccess = onSuccess
) {
}