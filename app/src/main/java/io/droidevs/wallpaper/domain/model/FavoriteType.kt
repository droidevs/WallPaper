package io.droidevs.wallpaper.domain.model

enum class FavoriteType {
    COLLECTION,
    TOPIC,
    PHOTO;

    companion object {
        fun fromString(type: String): FavoriteType {
            return valueOf(type.uppercase())
        }
    }
}