package io.droidevs.wallpaper.ui.viewmodels



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.droidevs.wallpaper.domain.TopicOrderBy
import io.droidevs.wallpaper.domain.model.Topic
import io.droidevs.wallpaper.domain.pager.SimpleCachingPaginator
import io.droidevs.wallpaper.domain.usecases.data.topic.TopicUseCases
import io.droidevs.wallpaper.ui.model.TopicUi
import io.droidevs.wallpaper.ui.model.mappers.toUiModel
import io.droidevs.wallpaper.ui.viewmodels.actions.TopicListScreenAction
import io.droidevs.wallpaper.ui.viewmodels.events.TopicListScreenEvents
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import io.droidevs.wallpaper.ui.viewmodels.state.TopicsState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class TopicListScreenViewModel @Inject constructor(
    private val useCases : TopicUseCases
) : ViewModel() {

    private var isRefreshing = false

    private val _state = MutableStateFlow(TopicsState())
    val state: StateFlow<TopicsState> = _state.asStateFlow()
        .onStart { loadState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TopicsState(topics = emptyList())
        )


    private val _events = MutableSharedFlow<TopicListScreenEvents>()
    val events: SharedFlow<TopicListScreenEvents> = _events


    suspend fun loadState(){
        loadMoreTopics()
    }
    private val paginator = SimpleCachingPaginator<Topic>(
            pageSize = 20,
            loadItems = { page, size ->
                useCases.getOnline(
                    page = page,
                    pageSize = size,
                    order = state.value.currentSortOrder
                ).first()
            },
            getItemsFromCache = { page, limit ->
                useCases.getOffline(
                    page = page,
                    pageSize = limit
                ).first()
            },
            cacheItems = { items ->
                useCases.cache(items)
            },
            clearCache = {
                useCases.clearCache()
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
                    topics = _state.value.topics + items.map { it.toUiModel() }
                )
            }
    )

    fun loadMoreTopics() {
        if (_state.value.state == LoadingMode.Append || _state.value.state == LoadingMode.Refresh) return

        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun refreshTopics() {
        viewModelScope.launch {
            isRefreshing = true
            paginator.reset()
            paginator.loadNextItems()
        }
    }

    fun handleAction(action: TopicListScreenAction) {
        when (action) {
            is TopicListScreenAction.ChangeSortOrder -> changeSortOrder(action.sortOrder)
            is TopicListScreenAction.TopicClicked -> onTopicClick(action.topic)
            is TopicListScreenAction.Refresh -> refreshTopics()
            else -> throw IllegalArgumentException("Unknown action $action")
        }
    }

    private fun changeSortOrder(sortOrder: TopicOrderBy) {
        if (state.value.currentSortOrder != sortOrder) {
            _state.update { it.copy(currentSortOrder = sortOrder) }
            refreshTopics()
        }
    }

    private fun onTopicClick(topic: TopicUi) {
        viewModelScope.launch {
            _events.emit(TopicListScreenEvents.NavigateToTopicScreen(topic.id))
        }
    }
}