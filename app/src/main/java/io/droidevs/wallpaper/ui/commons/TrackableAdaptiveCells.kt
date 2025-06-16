package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

class TrackableAdaptiveCells(val minSize: Dp) : GridCells {
    var currentColumnCount by mutableStateOf(0)
        private set

    override fun Density.calculateCrossAxisCellSizes(
        availableSize: Int,
        spacing: Int
    ): List<Int> {
        val cellSize = minSize.roundToPx()
        val columnCount = (availableSize + spacing) / (cellSize + spacing)
            .coerceAtLeast(1)
        currentColumnCount = columnCount
        return List(columnCount) {
            val size = (availableSize - spacing * (columnCount - 1)) / columnCount
            size
        }
    }
}