package io.droidevs.wallpaper.data.datastore


import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import io.droidevs.wallpaper.models.AutoWallpaperSettings
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AutoWallpaperSettingsSerializer : Serializer<AutoWallpaperSettings> {
    override val defaultValue: AutoWallpaperSettings = AutoWallpaperSettings()

    override suspend fun readFrom(input: InputStream): AutoWallpaperSettings {
        return try {
            Json.decodeFromString(
                AutoWallpaperSettings.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            throw CorruptionException("Cannot read WallpaperSettings", e)
        }
    }

    override suspend fun writeTo(t: AutoWallpaperSettings, output: OutputStream) {
        output.write(Json.encodeToString(AutoWallpaperSettings.serializer(), t).encodeToByteArray())
    }
}
