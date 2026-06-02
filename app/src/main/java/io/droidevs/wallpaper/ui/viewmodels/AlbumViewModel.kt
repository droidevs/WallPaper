package io.droidevs.wallpaper.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.data.mappers.toDomainModel
import io.droidevs.wallpaper.data.pager.impl.LocalWallpapersPaginator
import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import io.droidevs.wallpaper.ui.viewmodels.event.SortEvent
import io.droidevs.wallpaper.ui.viewmodels.state.LocalWallpaperListScreenState
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import io.droidevs.wallpaper.ui.viewmodels.state.SelectState
import kotlinx.coroutines.launch

class AlbumViewModel (
    val repository : LocalWallpaperRepository
) : ViewModel() {

    private var isRefreshing = false
    private var isLoadingMore = false

    var state by mutableStateOf(LocalWallpaperListScreenState())
    var selectState by mutableStateOf(SelectState())

    val paginator = LocalWallpapersPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            if(it){
                if(isRefreshing)
                    state = state.copy(mode = LoadingMode.Refresh)
                if(isLoadingMore)
                    state = state.copy(mode = LoadingMode.Append)
            }
            else {
                isLoadingMore = false
                isRefreshing = false
                state = state.copy(mode = LoadingMode.Ide)
            }
        },
        onRequest = { nextPage ->
            repository.getWallpapersPage(nextPage , 15).map {
                it.map {
                    it.toDomainModel()
                }
            }
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it.localizedMessage)
        },
        onSuccess = { items , newKey ->
            state = state.copy(
                wallpapers = state.wallpapers + items,
                endReached = items.isEmpty())
        }
    )


    fun refresh(){
        if (isRefreshing or isLoadingMore)
            return
        isRefreshing = true
        reset()
        loadNextItems()
    }

    fun loadMore(){
        if (isLoadingMore or isRefreshing)
            return
        isLoadingMore = true
        loadNextItems()
    }

    fun loadNextItems(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun reset(){
        state = state.copy(wallpapers = emptyList())
        paginator.reset()
    }

    fun setSelectedWallpapers(selectedWallpapers: ArrayList<LocalWallpaper>) {
        selectedWallpapers.forEach {
            select(it)
        }
    }

    fun select(wallpaper: LocalWallpaper){
        select(wallpaper.id)
    }
    fun select(wallpaperId : String){
        selectState.selectedItems.add(wallpaperId)
        selectState = selectState.copy(selectedCount = selectState.selectedCount + 1)
    }

    fun deselect(wallpaper: LocalWallpaper){
        deselect(wallpaper.id)
    }

    fun deselect(wallpaperId: String){
        selectState.selectedItems.remove(wallpaperId)
        selectState = selectState.copy(selectedCount = selectState.selectedCount - 1)
    }

    fun deselectAll(){
        selectState.selectedItems.clear()
    }

    fun resetSelectedWallpapersState() {
        selectState.selectedItems.clear()
    }

    fun selectFromLastToCurrent(wallpaper: LocalWallpaper) {
        val wallpapers = state.wallpapers
        val selectedWallpapers = selectState.selectedItems
        val currentIndex = wallpapers.indexOf(wallpaper)
        var lastSelectedIndex = -1
        var firstSelectedIndex = -1

        wallpapers.forEachIndexed { index, wallpaper ->
            if (selectedWallpapers.contains(wallpaper.id)){
                if (firstSelectedIndex == -1)
                    firstSelectedIndex = index
                lastSelectedIndex = index
            }
        }

        when {
            currentIndex > lastSelectedIndex -> {
                for (i in lastSelectedIndex..currentIndex) {
                    select(wallpapers.get(i))
                }
            }
            currentIndex < firstSelectedIndex -> {
                for (i in currentIndex..firstSelectedIndex) {
                    select(wallpapers.get(i))
                }
            }
            else -> { // I am not sure why it's here
                for (i in currentIndex..lastSelectedIndex) {
                    select(wallpapers.get(i))
                }
            }
        }
    }


    fun onSortEvent(event : SortEvent){
        when(event){
            SortEvent.SortAlphabetically -> {
                state.wallpapers.sortedBy { it.name }
            }
            SortEvent.SortByLastModified -> {
                state.wallpapers.sortedBy { it.dateModified }
            }
            SortEvent.SortByLastModifiedReverse -> {
                state.wallpapers.sortedByDescending { it.dateModified }
            }
            SortEvent.SortAlphabeticallyReverse -> {
                state.wallpapers.sortedByDescending { it.name }
            }
        }
    }

}