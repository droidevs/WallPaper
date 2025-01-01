package io.droidevs.wallpaper.infrastructure.repository


import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.droidevs.wallpaper.domain.Wallpaper
import io.droidevs.wallpaper.infrastructure.datasource.dao.WallpaperDao
import io.droidevs.wallpaper.infrastructure.datasource.instances.WallpaperDatabase
import io.droidevs.wallpaper.infrastructure.model.WallpaperEntity
import io.droidevs.wallpaper.infrastructure.util.SortOrder
import io.droidevs.wallpaper.infrastructure.util.SortType
import io.droidevs.wallpaper.infrastructure.util.WallpaperSort
import io.droidevs.wallpaper.infrastructure.util.isOrderAsc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.selects.whileSelect


class WallpaperRepository(private val wallpaperDao: WallpaperDao) {

    suspend fun getWallpaperById(id: String): WallpaperEntity? {
        return wallpaperDao.getWallpaperByID(id)
    }

    suspend fun insertWallpaper(wallpaper: WallpaperEntity) {
        wallpaperDao.insert(wallpaper)
    }

    suspend fun updateWallpaper(wallpaper: WallpaperEntity) {
        wallpaperDao.update(wallpaper)
    }

    suspend fun deleteWallpaper(wallpaper: WallpaperEntity) {
        wallpaperDao.delete(wallpaper)
    }

    suspend fun purgeInvalidWallpapers(context : Context) {
        WallpaperDatabase.getInstance(context)?.let {
            wallpaperDao.purgeNonExistingWallpapers(it)
        }
    }

    fun getAllWallpapers(): Flow<List<WallpaperEntity>> = wallpaperDao.getWallpapersFlow()


    fun getWallpapers(page: Int, pageSize : Int,sort: WallpaperSort) : List<WallpaperEntity>{
        val startIndex = page*pageSize
        return with(sort){
            when(sortType){
                SortType.NAME -> {
                    if (sortOrder == SortOrder.ASC)
                        wallpaperDao.getWallpapersPageByNameASC(startIndex,pageSize)
                    else
                        wallpaperDao.getWallpapersPageByNameDEC(startIndex,pageSize)
                }
                SortType.WIDTH -> {
                    if (sortOrder == SortOrder.ASC)
                        wallpaperDao.getWallpapersPageByWidthASC(startIndex,pageSize)
                    else
                        wallpaperDao.getWallpapersPageByWidthDESC(startIndex,pageSize)
                }
                SortType.HEIGHT -> {
                    if (sortOrder == SortOrder.ASC)
                        wallpaperDao.getWallpapersPageByHeightASC(startIndex,pageSize)
                    else
                        wallpaperDao.getWallpapersPageByHeightDESC(startIndex,pageSize)
                }
                SortType.DATE -> {
                    if (sortOrder == SortOrder.ASC)
                        wallpaperDao.getWallpapersPageByDateASC(startIndex,pageSize)
                    else
                        wallpaperDao.getWallpapersPageByDateDESC(startIndex,pageSize)
                }
                else -> {
                    if (sortOrder == SortOrder.ASC)
                        wallpaperDao.getWallpapersPageByDateASC(startIndex,pageSize)
                    else
                        wallpaperDao.getWallpapersPageByDateDESC(startIndex,pageSize)
                }
            }
        }
    }



    fun getWallpapersPage(page : Int , pageSize : Int, sort: WallpaperSort = WallpaperSort(SortType.DATE,SortOrder.DESC)) : Result<List<WallpaperEntity>>{
        return Result.success(
            getWallpapers(page,pageSize,sort)
        )
    }


    /*

     fun getWallpapersPager(sort : WallpaperSort): Pager<Int, WallpaperEntity> {
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
    }*/

}