package io.droidevs.wallpaper.data.repository


import android.content.Context
import io.droidevs.wallpaper.data.local.dao.LocalWallpaperDao
import io.droidevs.wallpaper.data.local.AppDatabase
import io.droidevs.wallpaper.data.mappers.toDomainModel
import io.droidevs.wallpaper.data.mappers.toEntity
import io.droidevs.wallpaper.data.model.LocalWallpaperEntity
import io.droidevs.wallpaper.data.pager.impl.LocalWallpapersPaginator
import io.droidevs.wallpaper.data.util.SortOrder
import io.droidevs.wallpaper.data.util.SortType
import io.droidevs.wallpaper.data.util.WallpaperSort
import io.droidevs.wallpaper.domain.LocalWallpaper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LocalWallpaperRepository @Inject constructor(
    private val localWallpaperDao: LocalWallpaperDao
) {
    fun getWallpaperById(id: String): Flow<LocalWallpaper?> {
        return localWallpaperDao.getWallpaperByID(id)
            .map { it?.toDomainModel() }
    }

    suspend fun insertWallpaper(wallpaper: LocalWallpaper) {
        localWallpaperDao.insert(wallpaper.toEntity())
    }

    suspend fun updateWallpaper(wallpaper: LocalWallpaper) {
        localWallpaperDao.update(wallpaper.toEntity())
    }

    suspend fun deleteWallpaper(wallpaper: LocalWallpaper) {
        localWallpaperDao.delete(wallpaper.toEntity())
    }

    suspend fun purgeInvalidWallpapers(context: Context) {
        AppDatabase.getInstance(context)?.let {
            localWallpaperDao.purgeNonExistingWallpapers(it)
        }
    }

    fun getAllWallpapers(): Flow<List<LocalWallpaper>> {
        return localWallpaperDao.getWallpapersFlow()
            .map { list -> list.map { it.toDomainModel() } }
    }


    fun getWallpapersPage(
        page: Int,
        pageSize: Int,
        sort: WallpaperSort
    ): Flow<List<LocalWallpaper>> {
        return getWallpapers(
            page = page,
            pageSize = pageSize,
            sort = sort,
        ).map { wallpapers->
            wallpapers.map {
                it.toDomainModel()
            }
        }
    }
    private fun getWallpapers(
        page: Int,
        pageSize: Int,
        sort: WallpaperSort
    ): Flow<List<LocalWallpaperEntity>> {
        val startIndex = page * pageSize
        return when (sort.sortType) {
            SortType.NAME -> if (sort.sortOrder == SortOrder.ASC) {
                localWallpaperDao.getWallpapersPageByNameASC(startIndex, pageSize)
            } else {
                localWallpaperDao.getWallpapersPageByNameDEC(startIndex, pageSize)
            }
            SortType.WIDTH -> if (sort.sortOrder == SortOrder.ASC) {
                localWallpaperDao.getWallpapersPageByWidthASC(startIndex, pageSize)
            } else {
                localWallpaperDao.getWallpapersPageByWidthDESC(startIndex, pageSize)
            }
            SortType.HEIGHT -> if (sort.sortOrder == SortOrder.ASC) {
                localWallpaperDao.getWallpapersPageByHeightASC(startIndex, pageSize)
            } else {
                localWallpaperDao.getWallpapersPageByHeightDESC(startIndex, pageSize)
            }
            else -> if (sort.sortOrder == SortOrder.ASC) {
                localWallpaperDao.getWallpapersPageByDateASC(startIndex, pageSize)
            } else {
                localWallpaperDao.getWallpapersPageByDateDESC(startIndex, pageSize)
            }
        }
    }

    fun getWallpaperPaginator(
        pageSize: Int,
        sort: WallpaperSort = WallpaperSort(SortType.DATE, SortOrder.DESC),
        onLoadUpdated: (Boolean) -> Unit,
        onError: (Throwable) -> Unit,
        onSuccess: (data: List<LocalWallpaper>, hasMore: Int) -> Unit
    ): LocalWallpapersPaginator {
        return LocalWallpapersPaginator(
            initialKey = 1,
            onLoadUpdated = onLoadUpdated,
            onRequest = { currentKey ->
                try {
                    val data = getWallpapers(page = currentKey, pageSize = pageSize, sort = sort)
                        .first()
                    Result.success(data)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            },
            getNextKey = { prevKey, _ -> prevKey + 1 },
            onError = onError,
            onSuccess = { data, key->
                onSuccess(
                    data.map {
                            it.toDomainModel()
                        },
                    key
                )
            }
        )
    }
}