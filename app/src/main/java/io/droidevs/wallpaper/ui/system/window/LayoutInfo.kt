package io.droidevs.wallpaper.ui.system.window

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.ui.layouts.AppLayoutMode

data class AppLayoutInfo(
    val appLayoutMode: AppLayoutMode = AppLayoutMode.PHONE_PORTRAIT,
    val windowDpSize: DpSize =  DpSize(0.dp, 0.dp),
    val foldableInfo: FoldableInfo? = null
)