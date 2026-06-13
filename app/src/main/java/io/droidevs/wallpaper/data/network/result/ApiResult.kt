package io.droidevs.wallpaper.data.network.result

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.NetworkError

typealias ApiResult<T> = Result<T, NetworkError>
