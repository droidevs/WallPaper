package io.droidevs.wallpaper.domain.pager


import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.errors.DatabaseError
import io.droidevs.wallpaper.domain.result.errors.NetworkError
import kotlin.math.max

open class SimpleCachingPaginator<Item>(
    initialPage: Int = 1,
    pageSize: Int,
    private val loadItems: suspend (page: Int, pageSize: Int) -> Result<List<Item>, NetworkError>,
    private val getItemsFromCache: suspend (page: Int, limit: Int) -> Result<List<Item>, DatabaseError>,
    private val cacheItems: suspend (items: List<Item>) -> Result<List<Long>, DatabaseError>,
    private val clearCache: suspend () -> Result<Int, DatabaseError>,
    onLoadUpdated: (Boolean) -> Unit,
    private val onError: (error: DataError) -> Unit,
    private val onSuccess: (List<Item>, Boolean) -> Unit
) : CachingPaginator<Int, Item>(
    initialKey = initialPage,
    pageSize = pageSize,
    loadItems = { page, size -> loadItems(page, size) },
    getItemsFromCache = { page, limit -> getItemsFromCache(page, limit) },
    cacheItems = cacheItems,
    clearCache = clearCache,
    onLoadUpdated = onLoadUpdated,
    recalculateKey = { datasetSize, pageSize ->
        // Calculate which page we should be on based on dataset size
        max(1, (datasetSize / pageSize) + 1)
    },
    getNextKey = { currentPage, _ -> currentPage + 1 },
    onError = onError,
    onSuccess = onSuccess
) {

    suspend fun loadNextPage() = loadNextItems()

    suspend fun resetPaginator() = reset()

    fun getCurrentPage(): Int = currentKey
}