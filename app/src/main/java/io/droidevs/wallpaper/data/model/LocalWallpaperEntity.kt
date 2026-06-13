package io.droidevs.wallpaper.data.model

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.droidevs.wallpaper.data.util.SortType
import java.io.Serializable

@Entity(tableName = "wallpapers")
class LocalWallpaperEntity() : Comparable<LocalWallpaperEntity>, Serializable, Parcelable {

    @ColumnInfo(name = "name")
    var name: String? = null

    @SuppressLint("KotlinNullnessAnnotation")
    @ColumnInfo(name = "uri")
    @NonNull
    var uri: String = ""

    @ColumnInfo(name = "file_path")
    var filePath: String = ""

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = ""

    @ColumnInfo(name = "prominentColor")
    var prominentColor: Int = Color.TRANSPARENT

    @ColumnInfo(name = "width")
    var width: Int? = null

    @ColumnInfo(name = "height")
    var height: Int? = null

    @ColumnInfo(name = "dateModified")
    var dateModified: Long = 0

    @ColumnInfo(name = "size")
    var size: Long = 0

    @ColumnInfo(name = "albumId")
    var albumId: Int = 0


    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        uri = parcel.readString().toString()
        filePath = parcel.readString().toString()
        id = parcel.readString().toString()
        prominentColor = parcel.readInt()
        width = parcel.readValue(Int::class.java.classLoader) as? Int
        height = parcel.readValue(Int::class.java.classLoader) as? Int
        dateModified = parcel.readLong()
        size = parcel.readLong()
        albumId = parcel.readInt()
    }

    override fun toString(): String {
        return "Wallpaper(name=$name, uri=$uri, width=$width, height=$height)"
    }

    override fun compareTo(other: LocalWallpaperEntity): Int {
        val sortType = SortType.NAME
        //TODO(fetch the wallpapersort from settings)
        return when (sortType) {
            SortType.NAME -> name!!.compareTo(other.name!!)
            SortType.DATE -> dateModified.compareTo(other.dateModified)
            SortType.SIZE -> size.compareTo(other.size)
            SortType.WIDTH -> width!!.compareTo(other.width!!)
            SortType.HEIGHT -> height!!.compareTo(other.height!!)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LocalWallpaperEntity) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + uri.hashCode()
        result = 31 * result + (width ?: 0)
        result = 31 * result + (height ?: 0)
        result = 31 * result + dateModified.hashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + prominentColor
        result = 31 * result + albumId
        return result
    }

    fun isNull(): Boolean {
        return name == null || uri.isEmpty() || width == null || height == null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(uri)
        parcel.writeString(filePath)
        parcel.writeString(id)
        parcel.writeInt(prominentColor)
        parcel.writeValue(width)
        parcel.writeValue(height)
        parcel.writeLong(dateModified)
        parcel.writeLong(size)
        parcel.writeInt(albumId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocalWallpaperEntity> {
        override fun createFromParcel(parcel: Parcel): LocalWallpaperEntity {
            return LocalWallpaperEntity(parcel)
        }

        override fun newArray(size: Int): Array<LocalWallpaperEntity?> {
            return arrayOfNulls(size)
        }

        private const val TAG = "Wallpaper"
    }
}