package io.droidevs.wallpaper.ui.system

import androidx.compose.runtime.Composable
import io.droidevs.wallpaper.ui.system.window.LocalWindow
import io.droidevs.wallpaper.ui.system.window.WindowInfoController


data class SystemInfo(
    val statusBar : StatusBarController,
    val navigationBar : NavigationBarController,
    val window : WindowInfoController
){

}

object System{}

val System.info
    @Composable
    get() = SystemInfo(
        statusBar = System.statusBar,
        navigationBar = System.navigationBar,
        window = System.window)

val System.window
    @Composable
    get() = LocalWindow.current


val System.statusBar
    @Composable
    get() = LocalStatusBar.current

val System.navigationBar
    @Composable
    get() = LocalNavigationBar.current
