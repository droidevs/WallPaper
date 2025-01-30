package io.droidevs.wallpaper.coil.wallpaper

import coil3.map.Mapper
import coil3.request.Options
import io.droidevs.wallpaper.domain.Wallpaper

class WallpaperMapper : Mapper<Wallpaper , String> {

    override fun map(data: Wallpaper, options: Options): String {
        return data.uri.toString()
    }
}