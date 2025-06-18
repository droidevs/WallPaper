package io.droidevs.wallpaper.domain.usecases.data.collection

data class CollectionUseCases(
    val cache: CacheAllCollectionsUseCase,
    val clear: ClearCacheCollectionsUseCase,
    val getRemote: GetOnlineCollectionsUseCase,
    val getCache: GetCachedCollectionsUseCase,
    val getRelated: GetRelatedCollectionsUseCase,
    val getById: GetCollectionByIdUseCase
)