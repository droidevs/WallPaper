package io.droidevs.wallpaper.domain

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import io.droidevs.wallpaper.util.BitmapUtils.generatePalette
import java.io.File

data class LocalWallpaper(
    var id: String = "",
    var name: String? = null,
    var uri: Uri = Uri.EMPTY,
    var filePath: String? = null,
    var prominentColor: Int = Color.TRANSPARENT,
    var width: Int? = null,
    var height: Int? = null,
    var dateModified: Long = 0L,
    var size: Long = 0L,
    var albumID: Int = 0,
    var selected: Boolean = false
) {
    val isValid: Boolean
        get() = name != null && uri.toString().isNotEmpty() && width != null && height != null


    companion object {
        /**
         * Use this method to create a Wallpaper object from a URI only for files
         * that doesn't exist in the database and are not required to be associated
         * with a specific folder.
         *
         * [albumID] will return null
         *
         * @param uri The URI of the file
         */
        fun createFromUri(uri: Uri, context: Context): LocalWallpaper {
            val wallpaper = LocalWallpaper()
            wallpaper.uri = uri
            val documentFile = DocumentFile.fromSingleUri(context, uri)
            wallpaper.name = documentFile?.name
            wallpaper.size = documentFile?.length() ?: 0
            wallpaper.dateModified = documentFile?.lastModified() ?: 0

            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = false
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                wallpaper.width = options.outWidth
                wallpaper.height = options.outHeight
                wallpaper.prominentColor = bitmap?.generatePalette()?.vibrantSwatch?.rgb ?: 0
                wallpaper.id = uri.hashCode().toString()
            }

            return wallpaper
        }

        fun createWallpaperFromFile(file: DocumentFile, context: Context): LocalWallpaper {
            return createFromUri(file.uri, context)
        }

        /**
         * Use this method to create a Wallpaper object from a File.
         *
         * @param file The File object
         */
        fun createFromFile(file: File): LocalWallpaper{
            val wallpaper = LocalWallpaper()
            wallpaper.filePath = file.absolutePath
            wallpaper.name = file.name
            wallpaper.size = file.length()
            wallpaper.dateModified = file.lastModified()

            file.inputStream().use { inputStream ->
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = false
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                wallpaper.width = options.outWidth
                wallpaper.height = options.outHeight
                wallpaper.prominentColor = bitmap?.generatePalette()?.vibrantSwatch?.rgb ?: 0
                wallpaper.id = file.hashCode().toString()
            }

            return wallpaper
        }
    }
}
