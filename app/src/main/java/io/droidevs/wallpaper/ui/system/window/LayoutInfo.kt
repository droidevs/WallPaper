package io.droidevs.wallpaper.ui.system.window

import androidx.compose.ui.unit.DpSize
import io.droidevs.wallpaper.ui.layouts.AppLayoutMode

data class AppLayoutInfo(
    val appLayoutMode: AppLayoutMode,
    val windowDpSize: DpSize,
    val foldableInfo: FoldableInfo? = null
)