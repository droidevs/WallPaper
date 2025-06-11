package io.droidevs.wallpaper.domain.usecases.data.albums

data class AlbumUseCases(
    val getById: GetAlbumByIdUseCase,
    val getAll: GetAllAlbumsUseCase,
    val getPage: GetAlbumsPageUseCase,
    val delete: DeleteAlbumUseCase,
    val add : AddAlbumUseCase,
    val update: UpdateAlbumUseCase
)