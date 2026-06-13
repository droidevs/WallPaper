package io.droidevs.wallpaper.data.local.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import io.droidevs.wallpaper.data.local.AppDatabase
import io.droidevs.wallpaper.data.model.LocalWallpaperEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.File
import kotlin.system.measureTimeMillis

@Dao
interface LocalWallpaperDao {


    @Query("SELECT * FROM wallpapers ORDER BY name DESC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageByNameDEC(offset: Int, limit : Int) : Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY name ASC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageByNameASC(limit: Int, offset: Int): Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY size DESC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageBySizeDESC(limit: Int, offset: Int): Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY size ASC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageBySizeASC(limit: Int, offset: Int): Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY width DESC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageByWidthDESC(limit: Int, offset: Int): Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY width ASC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageByWidthASC(limit: Int, offset: Int): Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY height DESC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageByHeightDESC(limit: Int, offset: Int): Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY height ASC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageByHeightASC(limit: Int, offset: Int): Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY dateModified ASC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageByDateASC(limit: Int, offset: Int): Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY dateModified DESC LIMIT :limit OFFSET :offset")
    fun getWallpapersPageByDateDESC(limit: Int, offset: Int): Flow<List<LocalWallpaperEntity>>



    @Query("SELECT * FROM wallpapers ORDER BY dateModified DESC")
    fun getWallpapers(): Flow<List<LocalWallpaperEntity>>

    @Query("SELECT * FROM wallpapers ORDER BY dateModified DESC")
    fun getWallpapersFlow(): Flow<List<LocalWallpaperEntity>>

    /**
     * Get wallpaper by MD5
     */
    @Query("SELECT * FROM wallpapers WHERE id = :id")
    fun getWallpaperByID(id: String): Flow<LocalWallpaperEntity?>

    @Query("SELECT * FROM wallpapers WHERE id = :id")
    fun getWallpaperByIDSync(id: String): LocalWallpaperEntity?

    /**
     * Clean any entry that doesn't have any of the
     * specified extension
     *
     * Extensions: .jpg, .jpeg, .webp, .png
     * From: [LocalWallpaperEntity.name]
     */
    @Query("DELETE FROM wallpapers WHERE name NOT LIKE '%.jpg' AND name NOT LIKE '%.jpeg' AND name NOT LIKE '%.webp' AND name NOT LIKE '%.png'")
    fun sanitizeEntries()

    suspend fun getRandomWallpaper(): LocalWallpaperEntity {
        return getWallpapers().first().random()
    }

    /**
     * Delete a wallpaper from the database
     */
    @Delete
    fun delete(wallpaper: LocalWallpaperEntity)

    /**
     * Delete wallpaper by URI
     */
    @Query("DELETE FROM wallpapers WHERE uri = :uri")
    fun deleteByUri(uri: String)

    /**
     * Delete wallpaper by File
     */
    @Query("DELETE FROM wallpapers WHERE file_path = :path")
    fun deleteByFile(path: String)

    /**
     * Update a wallpaper from the database
     */
    @Update
    fun update(wallpaper: LocalWallpaperEntity)

    /**
     * Insert a wallpaper into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wallpaper: LocalWallpaperEntity)

    @Transaction
    fun insertWithConflictHandling(wallpaper: LocalWallpaperEntity) {
        val existingWallpaper = getWallpaperByIDSync(wallpaper.id)
        if (existingWallpaper != null) {
            wallpaper.id += "duplicate"
            Log.i("WallpaperDao", "Duplicate wallpaper found: ${wallpaper.id}")
        }

        insert(wallpaper)
    }

    /**
     * Delete the entire table
     */
    @Query("DELETE FROM wallpapers")
    fun nukeTable()

    /**
     * Delete all wallpapers by the matching the [LocalWallpaperEntity.albumId]
     * with the specified [hashcode]
     */
    @Query("DELETE FROM wallpapers WHERE albumId = :hashcode")
    fun removeByPathHashcode(hashcode: Int)


    suspend fun purgeNonExistingWallpapers(appDatabase: AppDatabase) {
        Log.i("WallpaperDao", "Purged non-existing or non-permitted wallpapers in: ${
            measureTimeMillis {
                val allPaths = listOf(""/*TODO()*/)
                val validHashcodes = allPaths.map { it.hashCode() }.toSet()

                val wallpaperDao = appDatabase.wallpaperDao()
                val allWallpapers = wallpaperDao.getWallpapers()

                allWallpapers.first().forEach { wallpaper ->
                    if (wallpaper.albumId !in validHashcodes || !File(wallpaper.filePath).exists()) {
                        wallpaperDao.delete(wallpaper)
                    }
                }
            }
        }")
    }
}