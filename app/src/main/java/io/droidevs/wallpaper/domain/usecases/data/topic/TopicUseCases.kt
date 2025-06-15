package io.droidevs.wallpaper.domain.usecases.data.topic

data class TopicUseCases(
    val getById: GetTopicByIdUseCase,
    val getOnline: GetOnlineTopicsUseCase,
    val getOffline: GetTopicsUseCase,
    val cache: CacheTopicsUseCase,
    val clearCache: ClearTopicCacheUseCase
)