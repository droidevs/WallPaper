package io.droidevs.wallpaper.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.droidevs.wallpaper.infrastructure.mappers.toDomainModel
import io.droidevs.wallpaper.infrastructure.pager.WallpapersPaginator
import io.droidevs.wallpaper.infrastructure.repository.WallpaperRepository
import io.droidevs.wallpaper.ui.viewmodels.state.LoadState
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import kotlinx.coroutines.launch

class WallpaperLoaderViewModel (
    val repository : WallpaperRepository
) : ViewModel() {

    var isRefreshing = false
    var isLoadingMore = false


    var state by mutableStateOf(LoadState())

    val paginator = WallpapersPaginator(
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
            state = state.copy(error = it?.localizedMessage)
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

}