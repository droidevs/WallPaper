package io.droidevs.wallpaper.domain.model

enum class ContentFilter(val apiValue: String) {
    LOW("low"),    // Default value - shows all content
    HIGH("high");  // Strict filtering

    companion object {
        fun fromString(value: String?): ContentFilter {
            return when (value?.lowercase()) {
                "high" -> HIGH
                else -> LOW  // Default if null or invalid
            }
        }
    }
}