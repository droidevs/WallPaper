package io.droidevs.wallpaper.data.network

import io.droidevs.wallpaper.data.network.exceptions.NotFound
import io.droidevs.wallpaper.data.network.exceptions.RateLimitExceeded
import io.droidevs.wallpaper.data.network.exceptions.runCatchingNetwork
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

open class ApiClient(val httpClient: HttpClient) {
    suspend inline fun <reified T> get(
        path: String,
        body: Any? = null,
        queryParams: Map<String, Any?> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Result<T, NetworkError> {
         return  runCatchingNetwork<T, NetworkError> {
            val response = httpClient.get(urlString = path) {
                // Add query parameters
                queryParams.forEach { (key, value) ->
                    value?.let { parameter(key, it) }
                }

                // Add headers
                headers.forEach { (key, value) ->
                    header(key, value)
                }

                // Set body if provided
                body?.let { setBody(it) }
            }

            when (response.status) {
                HttpStatusCode.TooManyRequests -> throw RateLimitExceeded()
                HttpStatusCode.NotFound -> throw NotFound()
                else -> {
                    NetworkError.UnknownError()
                }
            }
            response.body()
        }
    }
}