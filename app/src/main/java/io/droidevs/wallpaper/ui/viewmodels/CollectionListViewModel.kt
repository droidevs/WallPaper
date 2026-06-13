package io.droidevs.wallpaper.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.droidevs.wallpaper.domain.model.Collection
import io.droidevs.wallpaper.domain.pager.SimpleCachingPaginator
import io.droidevs.wallpaper.domain.pager.SimpleDefaultPaginator
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.onFailureSuspend
import io.droidevs.wallpaper.domain.result.onSuccessSuspend
import io.droidevs.wallpaper.domain.usecases.data.collection.CollectionUseCases
import io.droidevs.wallpaper.domain.usecases.favorites.collections.FavorCollectionUseCases
import io.droidevs.wallpaper.ui.model.collections.CollectionUi
import io.droidevs.wallpaper.ui.model.mappers.toUiModel
import io.droidevs.wallpaper.ui.viewmodels.actions.CollectionListScreenAction
import io.droidevs.wallpaper.ui.viewmodels.events.CollectionListScreenEvent
import io.droidevs.wallpaper.ui.viewmodels.events.CollectionListScreenEvent.*
import io.droidevs.wallpaper.ui.viewmodels.state.CollectionListScreenState
import io.droidevs.wallpaper.ui.viewmodels.state.FavoredCollectionListScreenState
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow


class CollectionListViewModel(
    private val collectionUseCases : CollectionUseCases,
    private val favorUseCases: FavorCollectionUseCases,
    private val appScope: CoroutineScope
) : ViewModel() {

    private var isRefreshing = false

    private var isFavoredRefreshing = false


    private val _state = MutableStateFlow(CollectionListScreenState())

    private val _favoredState = MutableStateFlow(FavoredCollectionListScreenState())

    val state = _state.asStateFlow()
        .onStart {
            loadState()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CollectionListScreenState()
        )

    val favoredState = _favoredState.asStateFlow()
        .onStart {
            loadFavoredState()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoredCollectionListScreenState()
        )


    private val _event = MutableSharedFlow<CollectionListScreenEvent>()
    val event = _event.asSharedFlow()


    private suspend fun loadState(){
        paginator.loadNextItems()
    }

    private suspend fun loadFavoredState(){
        favoredPaginator.loadNextItems()
    }


    private val paginator = SimpleCachingPaginator(
        pageSize = 20,
        loadItems = { page, size ->
            collectionUseCases.getRemote(
                searchQuery = state.value.searchQuery,
                page = page,
                pageSize = size
            ).first()
        },
        getItemsFromCache = { page, limit ->
            collectionUseCases.getCache(
                page = page,
                pageSize = limit
            ).first()
        },
        cacheItems = { items ->
            collectionUseCases.cache(items)
        },
        clearCache = {
            collectionUseCases.clear()
        },
        onLoadUpdated = {
            if (!it) {
                _state.value.copy(state = LoadingMode.Ide)
                isRefreshing = false
            }
            else {
                if (isRefreshing)
                    _state.update { it.copy(state = LoadingMode.Refresh) }
                else
                    _state.update { it.copy(state = LoadingMode.Append) }
            }
        },
        onError = { error ->
            _state.value = _state.value.copy(error = error as Error?)
        },
        onSuccess = { items, hasMore ->
            _state.value = _state.value.copy(
                collections = _state.value.collections + items.map { it.toUiModel() }
            )
        }
    )

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    private val favoredPaginator = SimpleDefaultPaginator<Collection>(
        initialKey = state.value.page,
        onLoadUpdated = {
            if (!it) {
                _favoredState.value.copy(state = LoadingMode.Ide)
                isFavoredRefreshing = false
            }
            else {
                if (isFavoredRefreshing)
                    _favoredState.update { it.copy(state = LoadingMode.Refresh) }
                else
                    _favoredState.update { it.copy(state = LoadingMode.Append) }
            }
        },
        onRequest = { nextPage ->
            favorUseCases.get.invoke(page = nextPage, pageSize = 10)
                .flatMapConcat { ids ->
                    flow {
                        ids.onSuccessSuspend {
                            val results = it.map { id ->
                                collectionUseCases.getById(id).first()
                            }.filter { result ->
                                result is Result.Success && result.data != null
                            }
                            val collections : ArrayList<Collection> = emptyList<Collection>() as ArrayList<Collection>
                            results.forEach { result ->
                                if (result is Result.Success) {
                                    collections.add(result.data!!)
                                }
                            }
                            emit(Result.Success(collections))
                        }
                        ids.onFailureSuspend {
                            emit(Result.Failure(it))
                        }
                    }
                }.first()
        },
        onSuccess = { items, hasMore ->
            _favoredState.value = _favoredState.value.copy(
                collections = _favoredState.value.collections + items.map { it.toUiModel() }
            )
        },
        onError = { error ->
            _favoredState.value = _favoredState.value.copy(error = error as Error?)
        }

    )

    fun onAction(action: CollectionListScreenAction) {
        when (action) {
            is CollectionListScreenAction.Refresh -> {
                viewModelScope.launch {
                    refresh()
                }
            }

            is CollectionListScreenAction.LoadingMore -> {
                viewModelScope.launch {
                    loadMore()
                }
            }

            is CollectionListScreenAction.Search -> {
                search(action.query)
            }

            is CollectionListScreenAction.CollectionClicked -> {
                appScope.launch {
                    _event.emit(NavigateToCollectionScreen(action.collection))
                }
            }

            is CollectionListScreenAction.Share -> {
                share(action.collection)
            }

            CollectionListScreenAction.LoadingMoreFavored -> {
                viewModelScope.launch {
                    loadMoreFavored()
                }
            }
            CollectionListScreenAction.RefreshFavored -> {
                viewModelScope.launch {
                    refreshFavored()
                }
            }
            is CollectionListScreenAction.SearchFavored -> {
                searchFavored(action.query)
            }
        }
    }

    private fun searchFavored(query: String) {
        _favoredState.value = _favoredState.value.copy(
            searchQuery = query
        )
    }

    private suspend fun refreshFavored() {
        isFavoredRefreshing = true
        _favoredState.value = _favoredState.value.copy(
            collections = emptyList(),
            page = 0,
            endReached = false
        )
        favoredPaginator.reset()
        favoredPaginator.loadNextItems()
    }

    private suspend fun loadMoreFavored() {
        favoredPaginator.loadNextItems()
    }

    private fun share(value: CollectionUi){

    }

    private fun search(query: String) {
        _state.value = _state.value.copy(
            searchQuery = query
        )
    }

    private suspend fun loadMore() {
        paginator.loadNextItems()
    }

    private suspend fun refresh() {
        isRefreshing = true
        _state.value = _state.value.copy(
            collections = emptyList(),
            page = 0,
            endReached = false
        )
        paginator.reset()
        paginator.loadNextItems()
    }
}