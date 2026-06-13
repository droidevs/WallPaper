package io.droidevs.wallpaper.domain.model

enum class ResizeMethod {
    NONE("none"),
    CROP("crop"),
    ZOOM("zoom"),
    FIT_WIDTH("fit_width"),
    FIT_HEIGHT("fit_height");

    val nameText: String
    constructor(name: String){
        nameText = name
    }

    companion object {
        fun fromString(value: String?): ResizeMethod {
            return entries.find { it.nameText == value } ?: NONE
        }
    }
}