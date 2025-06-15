package io.droidevs.wallpaper.domain.pager


import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.DataError
import io.droidevs.wallpaper.domain.result.errors.NetworkError
import io.droidevs.wallpaper.domain.result.map
import io.droidevs.wallpaper.domain.result.onFailure
import io.droidevs.wallpaper.domain.result.onSuccess
import io.droidevs.wallpaper.domain.result.onSuccessSuspend
import io.droidevs.wallpaper.domain.result.onSuccessSuspendWithResult
import kotlin.math.max


open class CachingPaginator<Key, Item>(
    private val initialKey: Key,
    private val pageSize: Int,
    private val loadItems: suspend (key: Key, pageSize: Int) -> Result<List<Item>, DataError>,
    private val getItemsFromCache: suspend (key: Key, limit: Int) -> Result<List<Item>,DataError>,
    private val cacheItems: suspend (items: List<Item>) -> Result<List<Long>,DataError>,
    private val clearCache: suspend () -> Result<Int,DataError>,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val recalculateKey: suspend (datasetSize: Int, pageSize: Int) -> Key,
    private val getNextKey: suspend (key: Key,items: List<Item>) -> Key,
    private val onError: (error: DataError) -> Unit,
    private val onSuccess: (List<Item>, Boolean) -> Unit
) : Paginator<Key, Item> {

    protected var currentKey = initialKey
    private var isMakingRequest = false
    private var endReached = false
    private var currentPageSize = pageSize

    override suspend fun loadNextItems() {
        if (isMakingRequest || endReached) return

        isMakingRequest = true
        onLoadUpdated(true)

        try {
            // First try to load from cache
            getItemsFromCache(currentKey, pageSize)
                .onSuccessSuspendWithResult { cacheItems ->
                    if (cacheItems.size == pageSize){
                        Result.Success(cacheItems)
                    }
                    else {
                        val needed = pageSize - cacheItems.size
                        val result = loadItems(currentKey, pageSize)
                        result.map {
                            (cacheItems as ArrayList).apply { addAll(it.takeLast(needed)) }
                        }
                    }
                }.onSuccessSuspend {
                    handleSuccess(it)
                    // Cache the items
                    cacheItems(it)
                }
                .onFailure {
                    onError(it)
                }

            isMakingRequest = false
            onLoadUpdated(false)

        } catch (e : Exception){
            //onError(DataError)
        }
        finally {
            isMakingRequest = false
            onLoadUpdated(false)
        }
    }

    suspend fun updatePageSize(newPageSize: Int, datasetSize: Int) {
        currentPageSize = newPageSize

        currentKey = recalculateKey(datasetSize, currentPageSize)

        ensureItemsAlignWithPageSize(datasetSize)
    }

    private suspend fun ensureItemsAlignWithPageSize(datasetSize: Int) {
        //todo : this is not stable
        val needed = currentPageSize - datasetSize % currentPageSize
        getItemsFromCache(currentKey, currentPageSize)
            .onSuccessSuspend { lastPageCache ->
                val cacheNeeded = if(lastPageCache.isEmpty()) 0 else currentPageSize - lastPageCache.size
                if (lastPageCache.size == currentPageSize){
                    onSuccess(lastPageCache.takeLast(needed), true)
                    getNextKey(currentKey,lastPageCache)
                }
                if (needed > 0 || cacheNeeded > 0){
                    val result = loadItems(currentKey, currentPageSize)
                    result.onSuccessSuspend {
                        cacheItems(it.takeLast(cacheNeeded))
                        val hasMore = it.size < max(needed,cacheNeeded)
                        onSuccess(it.takeLast(needed), hasMore)
                        endReached = !hasMore
                        getNextKey(currentKey,it)
                    }
                }
            }

    }


    private suspend fun handleSuccess(items: List<Item>, nexKey: Boolean = true) {
        val hasMore = items.size == pageSize
        endReached = !hasMore


        // todo check if the onSuccess accepts all items as unique
        if (nexKey && items.isNotEmpty()) {
            currentKey = getNextKey(currentKey,items)
        }

        onSuccess(items, hasMore)
    }

    override suspend fun reset() {
        currentKey = initialKey
        endReached = false
        clearCache()
    }
}