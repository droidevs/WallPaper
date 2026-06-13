package io.droidevs.wallpaper.ui.model


class PostWallpaperData {

    var oldSize: Long = 0
    var newSize: Long = 0
    var oldWidth: Int = 0
    var oldHeight: Int = 0
    var newWidth: Int = 0
    var newHeight: Int = 0
    var path: String = ""

    constructor()

    constructor(
        oldSize: Long,
        newSize: Long,
        oldWidth: Int,
        oldHeight: Int,
        newWidth: Int,
        newHeight: Int,
        path: String
    ) {
        this.oldSize = oldSize
        this.newSize = newSize
        this.oldWidth = oldWidth
        this.oldHeight = oldHeight
        this.newWidth = newWidth
        this.newHeight = newHeight
        this.path = path
    }

    val newAspectRatio: Float
        get() = newWidth.toFloat() / newHeight

    val oldAspectRatio: Float
        get() = oldWidth.toFloat() / oldHeight

    override fun toString(): String {
        return "PostWallpaperData{" +
                "oldSize=" + oldSize +
                ", newSize=" + newSize +
                ", oldWidth=" + oldWidth +
                ", oldHeight=" + oldHeight +
                ", newWidth=" + newWidth +
                ", newHeight=" + newHeight +
                ", path='" + path + '\'' +
                '}'
    }
}