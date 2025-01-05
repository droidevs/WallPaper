package io.droidevs.wallpaper.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.droidevs.wallpaper.infrastructure.model.AlbumEntity
import io.droidevs.wallpaper.infrastructure.repository.AlbumRepository
import kotlinx.coroutines.launch

class AlbumsViewModel(private val repository: AlbumRepository) : ViewModel() {

    fun addAlbum(album: AlbumEntity) {
        viewModelScope.launch {
            repository.addAlbum(album)
        }
    }

    fun getAllAlbums(onResult: (List<AlbumEntity>) -> Unit) {
        viewModelScope.launch {
            val albums = repository.getAllAlbums()
            onResult.invoke(albums)
        }
    }
}

