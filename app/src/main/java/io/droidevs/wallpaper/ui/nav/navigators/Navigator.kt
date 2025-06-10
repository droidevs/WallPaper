package io.droidevs.wallpaper.ui.nav.navigators

import io.droidevs.wallpaper.ui.nav.roots.Destination

interface Navigator {

    val currentStartDestination: Destination

    fun <T : Destination> navigateTo(destination: T)

    fun resetToStart()

    fun navigateUp()

}