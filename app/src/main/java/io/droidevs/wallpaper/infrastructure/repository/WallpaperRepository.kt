package io.droidevs.wallpaper.infrastructure.repository


import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.droidevs.wallpaper.infrastructure.datasource.dao.WallpaperDao
import io.droidevs.wallpaper.infrastructure.datasource.instances.WallpaperDatabase
import io.droidevs.wallpaper.infrastructure.model.Wallpaper
import io.droidevs.wallpaper.infrastructure.util.SortOrder
import io.droidevs.wallpaper.infrastructure.util.SortType
import io.droidevs.wallpaper.infrastructure.util.WallpaperSort
import io.droidevs.wallpaper.infrastructure.util.isOrderAsc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class WallpaperRepository(private val wallpaperDao: WallpaperDao) {

    suspend fun getWallpaperById(id: String): Wallpaper? {
        return wallpaperDao.getWallpaperByID(id)
    }

    suspend fun insertWallpaper(wallpaper: Wallpaper) {
        wallpaperDao.insert(wallpaper)
    }

    suspend fun updateWallpaper(wallpaper: Wallpaper) {
        wallpaperDao.update(wallpaper)
    }

    suspend fun deleteWallpaper(wallpaper: Wallpaper) {
        wallpaperDao.delete(wallpaper)
    }

    suspend fun purgeInvalidWallpapers(context : Context) {
        WallpaperDatabase.getInstance(context)?.let {
            wallpaperDao.purgeNonExistingWallpapers(it)
        }
    }

    fun getAllWallpapers(): Flow<List<Wallpaper>> = wallpaperDao.getWallpapersFlow()


    fun wallpapersPager(
        sort: WallpaperSort = WallpaperSort(SortType.DATE , SortOrder.DESC)
    ): Flow<PagingData<Wallpaper>>{
        return getWallpapersPager(sort).flow.flowOn(Dispatchers.IO)
    }

     fun getWallpapersPager(sort : WallpaperSort): Pager<Int, Wallpaper> {
         var list : ArrayList<Wallpaper>
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true,
                    maxSize = 100
                ),
                pagingSourceFactory = {
                    when(sort.sortType) {
                        SortType.NAME ->
                            if (isOrderAsc(sort.sortOrder)){
                                wallpaperDao.getWallpapersPagingSourceByNameASC()
                            }
                            else {
                                wallpaperDao.getWallpapersPagingSourceByNameDEC()
                            }
                        SortType.SIZE ->
                            if(isOrderAsc(sort.sortOrder)){
                                wallpaperDao.getWallpapersPagingSourceBySizeASC()
                            }
                            else {
                                wallpaperDao.getWallpapersPagingSourceBySizeDESC()
                            }
                        SortType.WIDTH ->
                            if (isOrderAsc(sort.sortOrder)){
                                wallpaperDao.getWallpapersPagingSourceByWidthASC()
                            }
                            else{
                                wallpaperDao.getWallpapersPagingSourceByWidthDESC()
                            }
                        else ->
                            wallpaperDao.getWallpapersPagingSource()
                    }
                }
            )
    }

}