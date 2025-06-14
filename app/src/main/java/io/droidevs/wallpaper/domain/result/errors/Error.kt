package io.droidevs.wallpaper.domain.result.errors

import io.droidevs.wallpaper.domain.result.RootError

sealed interface Error


data class InternalError(val cause: Throwable) : RootError

data class UnknownError(val cause: Throwable) : RootError