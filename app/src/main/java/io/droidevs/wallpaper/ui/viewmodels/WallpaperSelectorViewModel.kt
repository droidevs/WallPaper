package io.droidevs.wallpaper.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.droidevs.wallpaper.domain.Wallpaper
import io.droidevs.wallpaper.infrastructure.repository.WallpaperRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WallpaperSelectorViewModel(application: Application, wallpaperRepository: WallpaperRepository) : AndroidViewModel(application) {

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