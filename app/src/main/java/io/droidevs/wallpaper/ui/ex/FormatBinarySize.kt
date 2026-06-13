package io.droidevs.wallpaper.ui.ex

fun Long.formatBinarySize(): String {
    val kiloByteAsByte = 1.0 * 1024.0
    val megaByteAsByte = 1.0 * 1024.0 * 1024.0
    val gigaByteAsByte = 1.0 * 1024.0 * 1024.0 * 1024.0
    val teraByteAsByte = 1.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0
    val petaByteAsByte = 1.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0
    return when {
        this < kiloByteAsByte -> "${this.toDouble()} B"
        this >= kiloByteAsByte && this < megaByteAsByte ->
            "${
                String.format(
                    java.util.Locale.US,
                    "%.2f",
                    (this / kiloByteAsByte)
                )
            } KB"
        this >= megaByteAsByte && this < gigaByteAsByte ->
            "${
                String.format(
                    java.util.Locale.US,
                    "%.2f",
                    (this / megaByteAsByte)
                )
            } MB"
        this >= gigaByteAsByte && this < teraByteAsByte ->
            "${
                String.format(
                    java.util.Locale.US,
                    "%.2f",
                    (this / gigaByteAsByte)
                )
            } GB"
        this >= teraByteAsByte && this < petaByteAsByte ->
            "${
                String.format(
                    java.util.Locale.US,
                    "%.2f",
                    (this / teraByteAsByte)
                )
            } TB"
        else -> "Bigger than 1024 TB"
    }
}