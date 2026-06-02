package io.droidevs.wallpaper.domain.model

enum class PhotoOrientation(val apiValue: String) {
    LANDSCAPE("landscape"),
    PORTRAIT("portrait"),
    SQUARISH("squarish");

    companion object {
        fun fromString(value: String?): PhotoOrientation? {
            return values().firstOrNull { it.apiValue == value?.lowercase() }
        }
    }
}