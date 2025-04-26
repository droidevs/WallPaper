package io.droidevs.wallpaper.domain

enum class TopicOrderBy(val value: String) {
    FEATURED("featured"),
    LATEST("latest"),
    OLDEST("oldest"),
    POSITION("position");

    override fun toString() = value
}