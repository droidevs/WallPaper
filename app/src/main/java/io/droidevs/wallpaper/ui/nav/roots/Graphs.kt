package io.droidevs.wallpaper.ui.nav.roots


import kotlinx.serialization.Serializable

@Serializable
open class Graph(override val route: String) : Destination {

    @Serializable
    object App : Graph("app")

    @Serializable
    object Dashboard : Graph("home")

    @Serializable
    object Wallpaper : Graph("wallpaper")

    @Serializable
    object Albums : Graph("albums")

    companion object {
        val allGraphs = listOf(App, Dashboard, Wallpaper, Albums)
    }
}