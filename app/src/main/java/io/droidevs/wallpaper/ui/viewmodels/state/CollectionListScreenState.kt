package io.droidevs.wallpaper.ui.viewmodels.state


import io.droidevs.wallpaper.ui.model.collections.CollectionUi

data class CollectionListScreenState(
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val collections : List<CollectionUi> = emptyList(),
    val state: LoadingMode = LoadingMode.Ide,
    val endReached : Boolean = false,
    val page  : Int = 0,
    val error : Error? = null,
)