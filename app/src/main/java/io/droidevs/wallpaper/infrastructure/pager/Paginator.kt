package io.droidevs.wallpaper.infrastructure.pager

interface Paginator<key,Item> {
    suspend fun loadNextItems()

    fun reset()
}