package io.droidevs.wallpaper.ui.system.window

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

data class WindowSizeInfo(
    val width : Dp,
    val height : Dp
){

    val heightType = getSizeClass(height)

    val widthType = getSizeClass(width)

    val size = DpSize(width,height)


    fun getSizeClass(size : Dp) : WindowType{
        return when (size) {
            in 0.dp..600.dp -> WindowType.Compact
            in 600.dp..840.dp -> WindowType.Medium
            else -> WindowType.Expanded
        }
    }
}