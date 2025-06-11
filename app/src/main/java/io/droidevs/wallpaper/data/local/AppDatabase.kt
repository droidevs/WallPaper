package io.droidevs.wallpaper.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.droidevs.wallpaper.data.local.converters.Converters
import io.droidevs.wallpaper.data.local.dao.AlbumDao
import io.droidevs.wallpaper.data.local.dao.LocalWallpaperDao
import io.droidevs.wallpaper.data.local.dao.SearchHistoryDao
import io.droidevs.wallpaper.data.model.AlbumEntity
import io.droidevs.wallpaper.data.model.LocalWallpaperEntity
import io.droidevs.wallpaper.data.model.SearchHistoryEntity

@Database(entities =
    [AlbumEntity::class, SearchHistoryEntity::class], version = 1)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private const val DATABASE_NAME = "wallpapers"
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            kotlin.runCatching {
                if (!instance!!.isOpen) {
                    instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }.getOrElse {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance
        }

        fun destroyInstance() {
            if (instance?.isOpen == true) {
                instance?.close()
            }
            instance = null
        }
    }
}