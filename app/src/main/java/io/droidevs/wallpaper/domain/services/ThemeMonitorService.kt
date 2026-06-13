package io.droidevs.wallpaper.domain.services

interface ThemeMonitorService {

    suspend fun isNightMode()  : Boolean

}