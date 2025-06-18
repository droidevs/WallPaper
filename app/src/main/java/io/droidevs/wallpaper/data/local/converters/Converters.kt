package io.droidevs.wallpaper.data.local.converters


import androidx.room.TypeConverter
import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.domain.model.FavoriteType
import java.time.Instant

class Converters {
    // --- SearchScreenType Converter ---
    @TypeConverter
    fun fromSearchScreenType(type: SearchScreenType): String = type.name

    @TypeConverter
    fun toSearchScreenType(name: String): SearchScreenType = SearchScreenType.valueOf(name)

    // --- Instant (Timestamp) Converter ---
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }

    @TypeConverter
    fun toTimestamp(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }

    @TypeConverter
    fun fromFavoriteType(type: FavoriteType): String = type.typeName

    @TypeConverter
    fun toFavoriteType(name: String): FavoriteType = FavoriteType.fromString(name)
}