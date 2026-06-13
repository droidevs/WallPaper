package io.droidevs.wallpaper.domain.usecases.data.wallpaper

import javax.inject.Inject

class LocalWallpaperUseCases @Inject constructor(
    private val getWallpaperById: GetWallpaperByIdUseCase,
    private val insertWallpaper: InsertWallpaperUseCase,
    private val updateWallpaper: UpdateWallpaperUseCase,
    private val deleteWallpaper: DeleteWallpaperUseCase,
    private val purgeInvalidWallpapers: PurgeInvalidWallpapersUseCase,
    private val getAllWallpapers: GetAllWallpapersUseCase,
    private val getWallpapersPage: GetWallpapersPageUseCase,
    private val getWallpapersPaginator: GetWallpapersPaginatorUseCase
) {
    // Expose use cases as properties if needed
    fun getById() = getWallpaperById
    fun insert() = insertWallpaper
    fun update() = updateWallpaper
    fun delete() = deleteWallpaper
    fun purgeInvalid() = purgeInvalidWallpapers
    fun getAll() = getAllWallpapers
    fun getPage() = getWallpapersPage
    fun getPaginator() = getWallpapersPaginator
}