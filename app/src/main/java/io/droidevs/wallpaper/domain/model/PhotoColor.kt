package io.droidevs.wallpaper.domain.model

enum class PhotoColor(val apiValue: String) {
    BLACK_AND_WHITE("black_and_white"),
    BLACK("black"),
    WHITE("white"),
    YELLOW("yellow"),
    ORANGE("orange"),
    RED("red"),
    PURPLE("purple"),
    MAGENTA("magenta"),
    GREEN("green"),
    TEAL("teal"),
    BLUE("blue");

    companion object {
        fun fromString(value: String?): PhotoColor? {
            return entries.firstOrNull { it.apiValue == value?.lowercase() }
        }
    }
}