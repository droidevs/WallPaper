package io.droidevs.wallpaper.ui.viewmodels.state

data class SelectState(
    val selectedItems : ArrayList<String> = emptyList<String>() as ArrayList<String>,
    val selectedCount : Int = selectedItems.size,
    val selectMode : Boolean = selectedCount == 0,
)