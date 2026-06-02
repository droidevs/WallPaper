package io.droidevs.wallpaper.domain.model

enum class PhotoOrderBy(val apiValue: String) {
    RELEVANT("relevant"),  // Default value
    LATEST("latest");

    companion object {
        fun fromString(value: String?): PhotoOrderBy {
            return when (value?.lowercase()) {
                "latest" -> LATEST
                else -> RELEVANT  // Default if null or invalid
            }
        }
    }
}