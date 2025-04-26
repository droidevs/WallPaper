package io.droidevs.wallpaper.models

enum class ThemeMode(val value: Int, val nameText: String) {
    AUTO(0, "auto"),
    LIGHT(1, "light"),
    DARK(2, "dark");

    companion object {
        fun fromName(name: String?): ThemeMode {
            return values().find { it.nameText == name } ?: AUTO
        }
    }
}
