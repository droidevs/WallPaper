package io.droidevs.wallpaper.data.util

import java.io.File

class FileUtils {

    inline fun <T> createTempFile(block: (File) -> T): T {
        val file = File.createTempFile("exif", ".jpg")
        try {
            return block(file)
        } finally {
            file.delete()
        }
    }
}