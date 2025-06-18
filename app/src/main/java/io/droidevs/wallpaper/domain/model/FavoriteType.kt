package io.droidevs.wallpaper.domain.model

import io.droidevs.wallpaper.domain.result.errors.DataError

sealed class FavoriteType(val typeName: String) {
    object Collection : FavoriteType("collection")
    object Topic : FavoriteType("topic")
    object Photo : FavoriteType("photo")

    companion object {
        private val types = listOf(Collection, Topic, Photo)

        fun fromString(type: String): FavoriteType {
            return types.find { it.typeName == type.lowercase() }?: throw IllegalArgumentException("Invalid favorite type: $type")
        }
    }
}