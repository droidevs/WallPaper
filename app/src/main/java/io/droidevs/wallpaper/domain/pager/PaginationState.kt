package io.droidevs.wallpaper.domain.pager

import io.droidevs.wallpaper.domain.result.errors.DataError


sealed class PaginationState {
    object Refreshing : PaginationState()
    object Loading : PaginationState()
    object Idle : PaginationState()
    data class Error(val error: DataError) : PaginationState()
}