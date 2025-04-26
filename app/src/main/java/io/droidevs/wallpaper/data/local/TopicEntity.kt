package io.droidevs.wallpaper.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "topics")
data class TopicEntity(

    @ColumnInfo(name = "remoteId")
    val remote: String,

    val slug: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "published_at")
    val publishedAt: Long,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,

    @ColumnInfo(name = "starts_at")
    val startsAt: Long?,

    @ColumnInfo(name = "ends_at")
    val endsAt: Long?,

    @ColumnInfo(name = "total_photos")
    val totalPhotos: Int,

    @ColumnInfo(name = "cover_photo_url")
    val coverPhotoUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null
}
