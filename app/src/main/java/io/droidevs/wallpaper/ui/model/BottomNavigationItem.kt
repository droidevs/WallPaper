package io.droidevs.wallpaper.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val id: Int,
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val hasNews : Boolean,
    val badgeCount : Int? = 0
)