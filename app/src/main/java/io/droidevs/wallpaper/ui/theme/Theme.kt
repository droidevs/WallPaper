package io.droidevs.wallpaper.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

// Predefined dark color scheme for the app, used when dynamic colors are not enabled or unavailable.
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Predefined light color scheme for the app, used when dynamic colors are not enabled or unavailable.
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

/**
 * WallPaperTheme is the main theme composable for the app.
 * It determines which color scheme to apply based on system theme, dynamic color availability,
 * and user preferences.
 *
 * @param darkTheme Whether the app should use the dark theme. Defaults to the system setting.
 * @param dynamicColor Whether to enable dynamic colors (available on Android 12+).
 * @param content The composable content that will use the selected theme.
 */
@Composable
fun WallPaperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Automatically detects system dark mode.
    dynamicColor: Boolean = true, // Enables dynamic color if supported and requested.
    content: @Composable () -> Unit // Content composable that uses this theme.
) {
    // Call rememberColorScheme to calculate and cache the correct color scheme.
    val colorScheme = rememberColorScheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
    )

    // Apply the calculated color scheme and typography to MaterialTheme.
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Typography is another theme parameter, assumed to be defined elsewhere.
        content = content // Render the given composable content within this theme.
    )
}

/**
 * rememberColorScheme determines and remembers the appropriate ColorScheme based on the given parameters.
 * It leverages the `remember` API to cache the computed value and avoid unnecessary recomposition.
 *
 * @param darkTheme Whether the app should use the dark theme.
 * @param dynamicColor Whether to enable dynamic colors (available on Android 12+).
 * @return The selected ColorScheme to be used in the app's theme.
 */
@Composable
fun rememberColorScheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
): ColorScheme {
    // Obtain the current Android context, necessary for dynamic color APIs.
    val context = LocalContext.current

    // Use `remember` to cache the color scheme. It recalculates only when relevant inputs change:
    // - `context`: Needed for dynamic color APIs and reflects the app's environment.
    // - `darkTheme`: Determines whether to use dark or light colors.
    // - `dynamicColor`: Indicates whether to use Android's dynamic theming system.
    return remember(
        context, // Dependency on context, which might change during configuration changes.
        darkTheme, // Dependency on the selected theme mode.
        dynamicColor // Dependency on whether dynamic colors are enabled.
    ) {
        // Determine the color scheme based on the parameters.
        when {
            // Use dynamic color if available and requested.
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                if (darkTheme) {
                    dynamicDarkColorScheme(context) // Dynamic dark color scheme.
                } else {
                    dynamicLightColorScheme(context) // Dynamic light color scheme.
                }
            }
            // Fallback to predefined dark color scheme.
            darkTheme -> DarkColorScheme
            // Fallback to predefined light color scheme.
            else -> LightColorScheme
        }
    }
}
