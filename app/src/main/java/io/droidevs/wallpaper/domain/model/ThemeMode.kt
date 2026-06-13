package io.droidevs.wallpaper.domain.model

enum class ThemeMode(val value: Int, val nameText: String) {
    AUTO(0, "auto"),
    LIGHT(1, "light"),
    DARK(2, "dark");

    companion object {
        fun fromName(name: String?): ThemeMode {
            return entries.find { it.nameText == name } ?: AUTO
        }
    }
}
