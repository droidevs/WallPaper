package io.droidevs.wallpaper.data.network.exceptions

import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.NetworkError
import io.droidevs.wallpaper.domain.result.runCatchingResult
import io.droidevs.wallpaper.domain.result.runCatchingWithResult
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException


suspend fun <D , E : NetworkError> runCatchingNetwork(
    block: suspend () -> D
) = runCatchingResult(
    errorTransform = {e->
        e.toNetworkError()
    }
){
    block()
}

suspend fun <D , E : NetworkError> runCatchingNetworkWithResult(
    block: suspend () -> Result<D, E>
) = runCatchingWithResult(
    errorTransform = {e->
        e.toNetworkError()
    }
) {
    block()
}


fun Throwable.toNetworkError() : NetworkError {
    return when (this) {
        is RateLimitExceeded -> NetworkError.RateLimitExceeded(
            cause = this
        )
        is NotFound -> NetworkError.NotFound(
            cause = this
        )
        is ClientRequestException -> NetworkError.ClientError(
            cause = this
        )
        is ServerResponseException -> NetworkError.ServerError(
            cause = this
        )
        is IOException -> NetworkError.ConnectionError(
            cause = this
        )
        is SerializationException -> NetworkError.SerializationError(
            cause = this
        )
        else -> NetworkError.UnknownError(
            cause = this
        )
    }
}