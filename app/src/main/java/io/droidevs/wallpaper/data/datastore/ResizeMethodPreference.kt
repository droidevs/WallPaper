package io.droidevs.wallpaper.data.datastore

import io.droidevs.wallpaper.models.ResizeMethod

interface ResizeMethodPreference {
    suspend fun saveResizeMethod(method: ResizeMethod)
    suspend fun getResizeMethod(): ResizeMethod
}
