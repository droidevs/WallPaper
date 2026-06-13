package io.droidevs.wallpaper.domain.pager

import io.droidevs.wallpaper.domain.result.errors.DataError
import kotlinx.coroutines.flow.MutableSharedFlow
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.onFailure
import io.droidevs.wallpaper.domain.result.onSuccess
import io.droidevs.wallpaper.domain.result.onSuccessSuspend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

open class FlowPaginator<Key, Item>(
    private val initialKey: Key,
    private val onRequest: suspend (nextKey: Key) -> Result<List<Item>, DataError>,
    private val getNextKey: suspend (key: Key, items: List<Item>) -> Key
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    private val _paginatorFlow = MutableSharedFlow<List<Item>>(
        replay = 0,
        extraBufferCapacity = 64
    )
    val paginatorFlow: SharedFlow<List<Item>> = _paginatorFlow

    private val _state = MutableStateFlow<PaginationState>(PaginationState.Idle)
    val state: StateFlow<PaginationState> = _state

    override suspend fun loadNextItems() {
        if (isMakingRequest) return

        isMakingRequest = true
        _state.value = PaginationState.Loading
        onRequest(currentKey)
            .onSuccessSuspend {
                currentKey = getNextKey(currentKey, it)
                _paginatorFlow.emit(it)
            }.onFailure {
                _state.value = PaginationState.Error(it)
            }
        isMakingRequest = false
        _state.value = PaginationState.Idle
    }

    override suspend fun reset() {
        currentKey = initialKey
        _paginatorFlow.emit(emptyList())
    }

    suspend fun refresh(){
        if (isMakingRequest) return
        reset()
        _state.value = PaginationState.Refreshing

        isMakingRequest = true

        loadNextItems()

        _state.value = PaginationState.Idle
        isMakingRequest = false
    }
}