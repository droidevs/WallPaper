package io.droidevs.wallpaper.data.mappers

import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.data.model.AlbumEntity

fun AlbumEntity.toDomainModel(): Album {
    return Album(
        id = this.id,
        title = this.title,
        artist = this.artist,
        releaseYear = this.releaseYear,
        genre = this.genre,
        coverImageUrl = this.coverImageUrl,
        total = totalWallpapers
    )
}

fun Album.toEntity(): AlbumEntity {
    return AlbumEntity(
        id = this.id,
        title = this.title,
        artist = this.artist,
        releaseYear = this.releaseYear,
        genre = this.genre,
        coverImageUrl = this.coverImageUrl,
        totalWallpapers =  total
    )
}
