package io.droidevs.wallpaper.ui.model.albums

import io.droidevs.wallpaper.domain.Album


fun Album.toUiModel() : AlbumUi {
    return AlbumUi(
        id = id,
        title = title,
        description = description,
        coverImageUrl = coverImageUrl,
        total = total
    )
}

fun AlbumUi.toDomainModel() : Album {
    return Album(
        id = id,
        title = title,
        description = description,
        coverImageUrl = coverImageUrl,
        total = total
    )
}