package io.droidevs.wallpaper.infrastructure.datastore

sealed class Screen {

    object None : Screen()

    object Home : Screen()

    object Lock : Screen()

    object Both : Screen()

}