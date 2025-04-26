package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import io.droidevs.wallpaper.models.PhotoFilter
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


class PhotoFilterSerializer : Serializer<PhotoFilter> {
    override val defaultValue: PhotoFilter = PhotoFilter.DEFAULT

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    override suspend fun readFrom(input: InputStream): PhotoFilter {
        try {
            return json.decodeFromString(
                deserializer = PhotoFilter.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Unable to read PhotoFilter", e)
        }
    }

    override suspend fun writeTo(t: PhotoFilter, output: OutputStream) {
        try {
            output.write(
                json.encodeToString(
                    serializer = PhotoFilter.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Unable to write PhotoFilter", e)
        }
    }
}