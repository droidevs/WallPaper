package io.droidevs.wallpaper.data.pager.impl

import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.data.network.result.ApiResult
import io.droidevs.wallpaper.data.pager.CachingPaginator
import io.droidevs.wallpaper.data.pager.SimpleCachingPaginator

class TopicsPaginator(
    pageSize: Int,
    loadItems: suspend (key: Int, pageSize: Int) -> ApiResult<List<TopicEntity>>,
    getItemsFromCache: suspend (key: Int, limit: Int) -> List<TopicEntity>,
    cacheItems: suspend (items: List<TopicEntity>) -> Unit,
    clearCache: suspend () -> Unit,
    onLoadUpdated: (Boolean) -> Unit,
    onError: (Throwable) -> Unit,
    onSuccess: (List<TopicEntity>, Boolean) -> Unit,
) : SimpleCachingPaginator<TopicEntity>(
    pageSize = pageSize,
    loadItems = loadItems,
    getItemsFromCache = getItemsFromCache,
    cacheItems = cacheItems,
    clearCache = clearCache,
    onLoadUpdated = onLoadUpdated,
    onError = onError,
    onSuccess = onSuccess,
)