package io.droidevs.wallpaper.ui.model

import androidx.compose.ui.graphics.vector.ImageVector


data class MenuItem(
    val id: Int,
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    var isSelected : Boolean = false
)