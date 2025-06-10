package io.droidevs.wallpaper.ui.nav.roots

import kotlinx.serialization.Serializable


interface Destination {
    val route: String
}

object AppDestinations {
    val allDestinations = Screen.allScreens + Graph.allGraphs
}
