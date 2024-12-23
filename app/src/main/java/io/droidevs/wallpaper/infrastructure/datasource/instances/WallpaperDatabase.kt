package io.droidevs.wallpaper.infrastructure.datasource.instances

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.droidevs.wallpaper.infrastructure.datasource.dao.WallpaperDao
import io.droidevs.wallpaper.infrastructure.model.WallpaperEntity
import io.droidevs.wallpaper.util.ConditionUtils.invert

@Database(entities = [WallpaperEntity::class], version = 1)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperDao

    companion object {
        private const val DATABASE_NAME = "wallpapers"
        private var instance: WallpaperDatabase? = null

        @Synchronized
        fun getInstance(context: Context): WallpaperDatabase? {
            kotlin.runCatching {
                if (instance!!.isOpen.invert()) {
                    instance = Room.databaseBuilder(context, WallpaperDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }.getOrElse {
                instance = Room.databaseBuilder(context, WallpaperDatabase::class.java, DATABASE_NAME)
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