package io.droidevs.wallpaper.data.util

import io.droidevs.wallpaper.data.model.LocalWallpaperEntity

data class WallpaperSort(
    val sortType : SortType,
    val sortOrder : SortOrder
)
fun ArrayList<LocalWallpaperEntity>.getSortedList(sort: WallpaperSort) {
    //TODO(FETCH the sort data from settings)
    when (sort.sortType) {
        SortType.NAME -> sortByName(sort.sortOrder)
        SortType.DATE -> sortByDate(sort.sortOrder)
        SortType.SIZE -> sortBySize(sort.sortOrder)
        SortType.WIDTH -> sortByWidth(sort.sortOrder)
        SortType.HEIGHT -> sortByHeight(sort.sortOrder)
    }
}

fun List<LocalWallpaperEntity>.getSortedList(): List<LocalWallpaperEntity> {
    val sortedList = ArrayList(this) // Copy the list
    sortedList.getSortedList()
    return sortedList
}

private fun ArrayList<LocalWallpaperEntity>.sortByName(sortOrder: SortOrder) {
    if (isOrderAsc(sortOrder)) {
        sortBy { it.name }
    } else {
        sortByDescending { it.name }
    }
}

private fun ArrayList<LocalWallpaperEntity>.sortByDate(sortOrder: SortOrder) {
    if (isOrderAsc(sortOrder)) {
        sortBy { it.dateModified }
    } else {
        sortByDescending { it.dateModified }
    }
}

private fun ArrayList<LocalWallpaperEntity>.sortBySize(sortOrder: SortOrder) {
    if (isOrderAsc(sortOrder)) {
        sortBy { it.size }
    } else {
        sortByDescending { it.size }
    }
}

private fun ArrayList<LocalWallpaperEntity>.sortByWidth(sortOrder: SortOrder) {
    if (isOrderAsc(sortOrder)) {
        sortBy { it.width }
    } else {
        sortByDescending { it.width }
    }
}

private fun ArrayList<LocalWallpaperEntity>.sortByHeight(sortOrder: SortOrder) {
    if (isOrderAsc(sortOrder)) {
        sortBy { it.height }
    } else {
        sortByDescending { it.height }
    }
}

fun isOrderAsc(sortOrder: SortOrder): Boolean {
    return sortOrder /*TODO(fetch order from settings)*/ == SortOrder.ASC
}