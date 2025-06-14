package io.droidevs.wallpaper.ui.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.droidevs.wallpaper.data.model.AlbumEntity // Assuming this is your data model
import io.droidevs.wallpaper.domain.pager.SimpleDefaultPaginator
import io.droidevs.wallpaper.domain.usecases.data.albums.AlbumUseCases
import io.droidevs.wallpaper.ui.model.albums.toUiModel
import io.droidevs.wallpaper.ui.viewmodels.actions.AlbumListScreenAction
import io.droidevs.wallpaper.ui.viewmodels.events.AlbumListScreenEvent
import io.droidevs.wallpaper.ui.viewmodels.events.AlbumListScreenEvent.*
import io.droidevs.wallpaper.ui.viewmodels.state.AlbumListScreenState
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val albumUseCases: AlbumUseCases, // Made private val
    // applicationCoroutine: CoroutineScope // Consider if really needed, viewModelScope is often enough
) : ViewModel() {

    private var isRefreshing = false

    private val _state = MutableStateFlow(AlbumListScreenState())
    val state = _state.asStateFlow()
        .onStart { loadNextItems() } // We'll trigger this from init or refresh
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AlbumListScreenState()
        )

    private val _event = MutableSharedFlow<AlbumListScreenEvent>(
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
            albumUseCases.getPage(page = nextPage, pageSize = 10).first()
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
                    albums = currentState.albums + items.map {
                        it.toUiModel()
                    }, // Append new items
                    page = newKey,
                    endReached = items.isEmpty() // Assuming empty list means end is reached
                )
            }
        }
    )



    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun onAction(action: AlbumListScreenAction) {
        when (action) {
            is AlbumListScreenAction.ClickAlbum -> {
                viewModelScope.launch {
                    // Example: Navigate to album detail
                    _event.emit(NavigateToAlbumDetail(action.album.id))
                }
            }
            is AlbumListScreenAction.DeselectAlbum -> {
                viewModelScope.launch {
                    _state.update { currentState ->
                        currentState.copy(
                            albums = currentState.albums.map { album ->
                                if (album.id == action.album.id)
                                    album.copy(isSelected = false)
                                else
                                    album
                            }
                        )
                    }
                }
            }
            AlbumListScreenAction.DeselectAll -> {
                viewModelScope.launch {
                    _state.update { currentState ->
                        currentState.copy(
                            albums = currentState.albums.map { it.copy(isSelected = false) }
                        )
                    }
                }
            }
            is AlbumListScreenAction.SelectAlbum -> {
                viewModelScope.launch {
                    _state.update { currentState ->
                        currentState.copy(
                            albums = currentState.albums.map { album ->
                                if (album.id == action.album.id)
                                    album.copy(isSelected = true)
                                else
                                    album
                            }
                        )
                    }
                }
            }

            is AlbumListScreenAction.OnSearchAction -> {
                val searchQuery = _state.value.searchQuery
                if (searchQuery.isNotBlank()) {
                    viewModelScope.launch {
                        _event.emit(
                            NavigateToSearchScreen(
                                query = searchQuery
                            )
                        )
                    }
                }
            }
            is AlbumListScreenAction.SearchActiveChanged -> TODO()
            is AlbumListScreenAction.DeleteAlbum -> TODO()
            AlbumListScreenAction.DeleteAllSelected -> TODO()
            is AlbumListScreenAction.EditAlbum -> TODO()
            AlbumListScreenAction.LoadMoreAlbums -> TODO()
            AlbumListScreenAction.RefreshAlbums -> TODO()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing = true
            _state.update { it.copy(albums = emptyList(), page = 0, endReached = false, error = null) } // Reset state
            paginator.reset() // Reset paginator's internal state
            loadNextItems()   // Load first page
            isRefreshing = false
        }
    }
}
