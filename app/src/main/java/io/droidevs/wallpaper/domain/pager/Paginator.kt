package io.droidevs.wallpaper.domain.pager

interface Paginator<key,Item> {
    suspend fun loadNextItems()

    suspend fun reset()
}