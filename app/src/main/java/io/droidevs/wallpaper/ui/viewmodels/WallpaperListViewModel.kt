package io.droidevs.wallpaper.ui.viewmodels

import android.app.Application
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import io.droidevs.wallpaper.domain.Wallpaper
import io.droidevs.wallpaper.infrastructure.datasource.instances.WallpaperDatabase
import io.droidevs.wallpaper.infrastructure.mappers.toDomainModel
import io.droidevs.wallpaper.infrastructure.repository.WallpaperRepository
import io.droidevs.wallpaper.infrastructure.util.SortOrder
import io.droidevs.wallpaper.infrastructure.util.SortType
import io.droidevs.wallpaper.infrastructure.util.WallpaperSort

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WallpaperListViewModel(application: Application, wallpaperRepository: WallpaperRepository) : AndroidViewModel(application) {

    var lazyGridState: LazyStaggeredGridState by mutableStateOf(LazyStaggeredGridState(0, 0))

    private val _isSelectionMode = MutableStateFlow(false)
    val isSelectionMode: StateFlow<Boolean> get() = _isSelectionMode

    fun setSelectionMode(isSelectionMode: Boolean) {
        _isSelectionMode.value = isSelectionMode
    }

    private val _selectedWallpapers = MutableStateFlow<ArrayList<String>>((ArrayList<String>()))
    val selectedWallpapers: StateFlow<ArrayList<String>> get() = _selectedWallpapers

    val selectedCount : Int get() = _selectedWallpapers.value.size

    fun setSelectedWallpapers(selectedWallpapers: ArrayList<Wallpaper>) {
        selectedWallpapers.forEach {
            select(it)
        }
    }

    fun select(wallpaper: Wallpaper){
        wallpaper.selected = true
        selectedWallpapers.value.add(wallpaper.id)
    }

    fun deselect(wallpaper: Wallpaper){
        wallpaper.selected = false
        selectedWallpapers.value.remove(wallpaper.id)
    }

    val wallpaperPaggingFlow = {
        val sort = WallpaperSort(SortType.NAME,SortOrder.DESC) //TODO(GET IT FROM PREFERENCES)
        wallpaperRepository.getWallpapersPager(sort)
            .flow
            .map {
                paggingData ->
                paggingData.map {
                    it.toDomainModel()
                }
            }
    }

    fun deleteSelectedWallpapers() {
        viewModelScope.launch(Dispatchers.IO) {
            val wallpaperDatabase = WallpaperDatabase.getInstance(getApplication())
            val wallpaperDao = wallpaperDatabase?.wallpaperDao()

            selectedWallpapers.value.forEach {

            }
        }
    }

    fun resetSelectedWallpapersState() {
        selectedWallpapers.value.let {
            it.removeAll(it)
        }
    }

    fun selectFromLastToCurrent(wallpaper: Wallpaper , wallpapers: ArrayList<Wallpaper>) {
        val currentIndex = wallpapers.indexOf(wallpaper)
        var lastSelectedIndex = -1
        var firstSelectedIndex = -1

        wallpapers.forEachIndexed { index, wallpaper ->
            if (selectedWallpapers.value.contains(wallpaper.id)){
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
}