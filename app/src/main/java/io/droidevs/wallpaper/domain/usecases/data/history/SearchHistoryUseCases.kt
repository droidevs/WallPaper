package io.droidevs.wallpaper.domain.usecases.data.history

data class SearchHistoryUseCases(
    val clear: ClearSearchHistoryUseCase,
    val delete: DeleteSearchHistoryItemUseCase,
    val get: GetSearchHistoryUseCase
)