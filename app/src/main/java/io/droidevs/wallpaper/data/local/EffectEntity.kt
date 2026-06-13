package io.droidevs.wallpaper.data.local

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "effects")
data class EffectEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "blur_value")
    var blurValue: Float,

    @ColumnInfo(name = "brightness_value")
    var brightnessValue: Float,

    @ColumnInfo(name = "contrast_value")
    var contrastValue: Float,

    @ColumnInfo(name = "saturation_value")
    var saturationValue: Float,

    @ColumnInfo(name = "hue_red_value")
    var hueRedValue: Float,

    @ColumnInfo(name = "hue_green_value")
    var hueGreenValue: Float,

    @ColumnInfo(name = "hue_blue_value")
    var hueBlueValue: Float,

    @ColumnInfo(name = "scale_red_value")
    var scaleRedValue: Float,

    @ColumnInfo(name = "scale_green_value")
    var scaleGreenValue: Float,

    @ColumnInfo(name = "scale_blue_value")
    var scaleBlueValue: Float
) : Parcelable
