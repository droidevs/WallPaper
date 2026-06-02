package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import io.droidevs.wallpaper.domain.model.WallpaperEffects
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object WallpaperEffectsSerializer : Serializer<WallpaperEffects> {
    override val defaultValue: WallpaperEffects = WallpaperEffects()

    override suspend fun readFrom(input: InputStream): WallpaperEffects {
        return try {
            Json.decodeFromString(
                WallpaperEffects.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Cannot read WallpaperEffects", e)
        }
    }

    override suspend fun writeTo(t: WallpaperEffects, output: OutputStream) {
        output.write(
            Json.encodeToString(WallpaperEffects.serializer(), t).encodeToByteArray()
        )
    }
}

