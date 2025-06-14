package io.droidevs.wallpaper.ui.viewmodels.state

enum class LoadingMode {
    Append,
    Refresh,
    Ide;

    fun isLoading() : Boolean {
        return this!= Ide
    }
}