package io.droidevs.wallpaper.di.usecases

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import io.droidevs.wallpaper.domain.usecases.data.wallpaper.DeleteWallpaperUseCase
import io.droidevs.wallpaper.domain.usecases.data.wallpaper.GetAllWallpapersUseCase
import io.droidevs.wallpaper.domain.usecases.data.wallpaper.GetWallpaperByIdUseCase
import io.droidevs.wallpaper.domain.usecases.data.wallpaper.GetWallpapersPageUseCase
import io.droidevs.wallpaper.domain.usecases.data.wallpaper.GetWallpapersPaginatorUseCase
import io.droidevs.wallpaper.domain.usecases.data.wallpaper.InsertWallpaperUseCase
import io.droidevs.wallpaper.domain.usecases.data.wallpaper.LocalWallpaperUseCases
import io.droidevs.wallpaper.domain.usecases.data.wallpaper.PurgeInvalidWallpapersUseCase
import io.droidevs.wallpaper.domain.usecases.data.wallpaper.UpdateWallpaperUseCase

@Module
@InstallIn(SingletonComponent::class)
object LocalWallpaperUseCaseModule {

    @Provides
    fun provideGetWallpaperByIdUseCase(repository: LocalWallpaperRepository): GetWallpaperByIdUseCase {
        return GetWallpaperByIdUseCase(repository)
    }

    @Provides
    fun provideInsertWallpaperUseCase(repository: LocalWallpaperRepository): InsertWallpaperUseCase {
        return InsertWallpaperUseCase(repository)
    }

    @Provides
    fun provideUpdateWallpaperUseCase(repository: LocalWallpaperRepository): UpdateWallpaperUseCase {
        return UpdateWallpaperUseCase(repository)
    }

    @Provides
    fun provideDeleteWallpaperUseCase(repository: LocalWallpaperRepository): DeleteWallpaperUseCase {
        return DeleteWallpaperUseCase(repository)
    }

    @Provides
    fun providePurgeInvalidWallpapersUseCase(repository: LocalWallpaperRepository): PurgeInvalidWallpapersUseCase {
        return PurgeInvalidWallpapersUseCase(repository)
    }

    @Provides
    fun provideGetAllWallpapersUseCase(repository: LocalWallpaperRepository): GetAllWallpapersUseCase {
        return GetAllWallpapersUseCase(repository)
    }

    @Provides
    fun provideGetWallpapersPageUseCase(repository: LocalWallpaperRepository): GetWallpapersPageUseCase {
        return GetWallpapersPageUseCase(repository)
    }

    @Provides
    fun provideGetWallpapersPaginatorUseCase(repository: LocalWallpaperRepository): GetWallpapersPaginatorUseCase {
        return GetWallpapersPaginatorUseCase(repository)
    }

    @Provides
    fun provideLocalWallpaperUseCases(
        getWallpaperById: GetWallpaperByIdUseCase,
        insertWallpaper: InsertWallpaperUseCase,
        updateWallpaper: UpdateWallpaperUseCase,
        deleteWallpaper: DeleteWallpaperUseCase,
        purgeInvalidWallpapers: PurgeInvalidWallpapersUseCase,
        getAllWallpapers: GetAllWallpapersUseCase,
        getWallpapersPage: GetWallpapersPageUseCase,
        getWallpapersPaginator: GetWallpapersPaginatorUseCase
    ): LocalWallpaperUseCases {
        return LocalWallpaperUseCases(
            getWallpaperById,
            insertWallpaper,
            updateWallpaper,
            deleteWallpaper,
            purgeInvalidWallpapers,
            getAllWallpapers,
            getWallpapersPage,
            getWallpapersPaginator
        )
    }
}