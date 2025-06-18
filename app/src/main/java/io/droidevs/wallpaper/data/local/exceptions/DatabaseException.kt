package io.droidevs.wallpaper.data.local.exceptions


sealed class DatabaseException : Exception() {

    class NoElementFound : DatabaseException()
    class ConstraintViolated : DatabaseException()
}