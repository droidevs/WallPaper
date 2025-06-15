package io.droidevs.wallpaper.data.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.*

val ktorClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json() // Configure your JSON serializer here
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
    defaultRequest {
        url("https://api.unsplash.com/")
        header("Content-Type", "application/json")
    }
}