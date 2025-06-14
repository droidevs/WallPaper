package io.droidevs.wallpaper.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.domain.pager.SimpleDefaultPaginator
import io.droidevs.wallpaper.domain.result.map
import io.droidevs.wallpaper.domain.result.mapResult
import io.droidevs.wallpaper.domain.result.onFailure
import io.droidevs.wallpaper.domain.result.onFailureWithResult
import io.droidevs.wallpaper.domain.result.onSuccess
import io.droidevs.wallpaper.domain.result.onSuccessWithResult
import io.droidevs.wallpaper.domain.usecases.data.history.SearchHistoryUseCases
import io.droidevs.wallpaper.ui.model.albums.toUiModel
import io.droidevs.wallpaper.ui.model.mappers.toUiModel
import io.droidevs.wallpaper.ui.viewmodels.actions.SearchScreenAction
import io.droidevs.wallpaper.ui.viewmodels.events.AlbumListScreenEvent
import io.droidevs.wallpaper.ui.viewmodels.events.SearchScreenEvent
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import io.droidevs.wallpaper.ui.viewmodels.state.SearchScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    val searchUseCases: SearchHistoryUseCases,
    val saveStateHandle: SavedStateHandle,
    val appCoroutineScope: CoroutineScope
) : ViewModel() {

    private var screenType: SearchScreenType = saveStateHandle["screenType"] ?: SearchScreenType.All

    private var isRefreshing = false

    private var  _state = MutableStateFlow(SearchScreenState())

    val state = _state.asStateFlow()
        .onStart {
            loadState()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchScreenState()
        )

    private val _event = MutableSharedFlow<SearchScreenEvent>(
        replay = 0, // Usually 0 for events, unless you specifically need replay
        extraBufferCapacity = 3 // Standard buffer size often sufficient
    )
    val event = _event.asSharedFlow()


    private val paginator = SimpleDefaultPaginator(
        initialKey = state.value.page, // Or 0 if you always start from the beginning
        onLoadUpdated = { isLoading ->
            if (!isLoading) {
                _state.update { it.copy(state = LoadingMode.Ide) }
            } else {
                if (isRefreshing){
                    _state.update { it.copy(state = LoadingMode.Refresh) }
                } else {
                    _state.update { it.copy(state = LoadingMode.Append) }
                }
            }
        },
        onRequest = { nextPage ->
            // Assuming your use case returns Result<List<AlbumEntity>>
            // and takes a page number.
            // Adjust if your use case is different (e.g., uses a limit/offset)
            if (_state.value.query.isEmpty()){
                searchUseCases.get(
                    screenType = screenType,
                    query = "",
                    page = nextPage, pageSize = 10).first()
            } else {
                searchUseCases.get(
                    screenType = screenType,
                    query = state.value.query,
                    page = nextPage,
                    pageSize = 10
                ).first()
            }
        },
        getNextKey = { key, items ->
            // Assuming AlbumEntity has an 'id' or some way to determine the next page
            // Or if your API returns page info, use that.
            // For simple sequential pages:
            key + 1
        },
        onError = { error ->
            _state.update { it.copy(error = error as Error?) }
            // Optionally emit an event to show a toast or dialog
            // viewModelScope.launch { _event.emit(AlbumListScreenEvent.ShowError(throwable?.message ?: "Unknown error")) }
        },
        onSuccess = { items, newKey ->
            _state.update { currentState ->
                currentState.copy(
                    suggestions = currentState.suggestions + items.map {
                        it.toUiModel()
                    }, // Append new items
                    page = newKey,
                    endReached = items.isEmpty() // Assuming empty list means end is reached
                )
            }
        }
    )

    suspend fun loadState(){
        _state.value = _state.value.copy(
            query = saveStateHandle["query"] ?: ""
        )
        val recentSuggestions = searchUseCases.get(
            screenType = screenType
        )

        val suggestions = recentSuggestions
            .mapResult { suggestions ->
            suggestions.map {
                it.toUiModel()
            }
        }.onSuccess { result ->
            _state.value = _state.value.copy(
                recentSuggestions = result
            )
        }.onFailure { result->
            _state.value = _state.value.copy(
                error = result as Error
            )
        }

    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing = true
            _state.update {
                it.copy(
                    suggestions = emptyList(),
                    page = 0,
                    endReached = false,
                    error = null
                )
            } // Reset state
            paginator.reset() // Reset paginator's internal state
            loadNextItems()   // Load first page
            isRefreshing = false
        }
    }

    fun onAction(action: SearchScreenAction){
        when (action) {
            SearchScreenAction.ClearSearch -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        query = ""
                    )
                }
                saveStateHandle["query"] = ""
            }
            is SearchScreenAction.DeleteSuggestion -> {
                appCoroutineScope.launch {
                    searchUseCases.delete(action.suggestion.id)
                }
            }
            SearchScreenAction.OnBackPressed -> {
                viewModelScope.launch {
                    _event.emit(SearchScreenEvent.NavigateBack)
                }
            }
            is SearchScreenAction.PickSuggestion -> {
                viewModelScope.launch {
                    _event.emit(
                        SearchScreenEvent.Search (
                            query = action.suggestion.query,
                            screenType = screenType
                        )
                    )
                }
            }
            is SearchScreenAction.Search -> {
                viewModelScope.launch {
                    _event.emit(
                        SearchScreenEvent.Search (
                            query = _state.value.query,
                            screenType = screenType
                        )
                    )
                }
            }
            is SearchScreenAction.UpdateQuery -> {
                _state.value = _state.value.copy(
                    query = action.query
                )
                refresh()
            }
        }
    }
}