package io.droidevs.wallpaper.domain.services.source

import java.io.InputStream

interface ByteSource {
    fun openStream(): InputStream
    fun toByteArray(): ByteArray
}