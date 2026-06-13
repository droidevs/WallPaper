package io.droidevs.wallpaper.data.mappers

import android.net.Uri
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.data.model.LocalWallpaperEntity

fun LocalWallpaperEntity.toDomainModel(): LocalWallpaper {
    return LocalWallpaper(
        id = this.id,
        name = this.name,
        uri = Uri.parse(this.uri),
        filePath = this.filePath.takeIf { it.isNotEmpty() },
        prominentColor = this.prominentColor,
        width = this.width,
        height = this.height,
        dateModified = this.dateModified,
        size = this.size,
        albumID = this.albumId
    )
}

fun LocalWallpaper.toEntity(): LocalWallpaperEntity {
    return LocalWallpaperEntity().apply {
        this.id = this@toEntity.id
        this.name = this@toEntity.name
        this.uri = this@toEntity.uri.toString()
        this.filePath = this@toEntity.filePath ?: ""
        this.prominentColor = this@toEntity.prominentColor
        this.width = this@toEntity.width
        this.height = this@toEntity.height
        this.dateModified = this@toEntity.dateModified
        this.size = this@toEntity.size
        this.albumId = this@toEntity.albumID
    }
}

