package io.droidevs.wallpaper.domain.usecases.favorites.collections

data class FavorCollectionUseCases(
    val get : GetFavoritesCollectionsUseCase,
    val unFavor: UnfavoriseCollectionUseCase,
    val favor: FavoriseCollectionUseCase,
    val isFavor: IsCollectionFavoritedUseCase,
    val count : CountFavoritesCollectionUseCase
)