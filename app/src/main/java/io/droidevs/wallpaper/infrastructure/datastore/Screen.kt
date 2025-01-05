package io.droidevs.wallpaper.infrastructure.datastore

sealed class Screen {

    object Home : Screen()

    object Lock : Screen()

    object Both : Screen()

}