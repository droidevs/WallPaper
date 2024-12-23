package io.droidevs.wallpaper.infrastructure.mappers

import android.net.Uri
import io.droidevs.wallpaper.domain.Wallpaper
import io.droidevs.wallpaper.infrastructure.model.WallpaperEntity

fun WallpaperEntity.toDomainModel(): Wallpaper {
    return Wallpaper(
        id = this.id,
        name = this.name,
        uri = Uri.parse(this.uri),
        filePath = this.filePath.takeIf { it.isNotEmpty() },
        prominentColor = this.prominentColor,
        width = this.width,
        height = this.height,
        dateModified = this.dateModified,
        size = this.size,
        folderID = this.folderID
    )
}

fun Wallpaper.toEntity(): WallpaperEntity {
    return WallpaperEntity().apply {
        this.id = this@toEntity.id
        this.name = this@toEntity.name
        this.uri = this@toEntity.uri.toString()
        this.filePath = this@toEntity.filePath ?: ""
        this.prominentColor = this@toEntity.prominentColor
        this.width = this@toEntity.width
        this.height = this@toEntity.height
        this.dateModified = this@toEntity.dateModified
        this.size = this@toEntity.size
        this.folderID = this@toEntity.folderID
    }
}

