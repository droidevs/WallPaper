package io.droidevs.wallpaper.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.droidevs.wallpaper.domain.model.FavoriteType


@Entity(
    tableName = "favorites",
    indices = [
        Index(value = ["itemId", "itemType"], unique = true),
        Index(value = ["itemType"]),
        Index(value = ["favoritedAt"], orders = [Index.Order.DESC])
    ]
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val itemId: String,

    @ColumnInfo(name = "itemType")
    val type: FavoriteType,

    @ColumnInfo(name = "favoritedAt", index = true)
    val favoritedAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun createId(type: FavoriteType, id: Long) = "${type.typeName.lowercase()}_$id"
    }
}