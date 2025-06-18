package io.droidevs.wallpaper.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "collections")
data class CollectionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "remote_id")
    val remoteId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "total_photos")
    val totalPhotos: Int,

    @ColumnInfo(name = "publish_time")
    val publishTime: Long,

    @ColumnInfo(name = "update_time")
    val updateTime: Long,

    @ColumnInfo(name = "cover_url")
    val coverUrl: String,

    @ColumnInfo(name = "cover_width")
    val coverWidth: Int,

    @ColumnInfo(name = "cover_height")
    val coverHeight: Int
)