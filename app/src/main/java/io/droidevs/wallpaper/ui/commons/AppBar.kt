package io.droidevs.wallpaper.ui.commons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.ui.theme.blueYellowGradient

/**
 * A customizable AppBar composable that supports animated visibility of navigation icons and actions.
 *
 * @param navigationIcon A composable lambda representing the navigation icon.
 * @param actions A composable lambda representing the actions in the AppBar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name)) // Displays the app's name from resources.
        },
        Modifier.background(MaterialTheme.colorScheme.primary), // Sets the background color of the AppBar.
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary, // Background color of the AppBar.
            titleContentColor = MaterialTheme.colorScheme.onPrimary, // Color of the title text.
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary // Color of the navigation icon.
        ),
        navigationIcon = {
            AnimatedVisibility(
                visible = true, // Controls visibility of the navigation icon.
                enter = slideInHorizontally(), // Sliding animation when it becomes visible.
                exit = slideOutHorizontally(), // Sliding animation when it disappears.
            ) {
                navigationIcon() // Renders the navigation icon composable.
            }
        },
        actions = {
            AnimatedVisibility(
                visible = true, // Controls visibility of the actions.
                enter = slideInHorizontally(), // Sliding animation when actions appear.
                exit = slideOutHorizontally(), // Sliding animation when actions disappear.
            ) {
                actions() // Renders the actions composable.
            }
        }
    )
}

/**
 * A specialized AppBar with a back button.
 *
 * @param onBackPressed Lambda function to handle back button press events.
 */
@Composable
fun BackAppBar(
    onBackPressed: () -> Unit,
) {
    AppBar(
        navigationIcon = {
            BackButton(
                onClick = {
                    onBackPressed.invoke() // Invokes the back press action.
                }
            )
        },
        actions = {

        }
    )
}

/**
 * A specialized AppBar with a menu button and a settings button.
 *
 * @param onMenuPressed Lambda function to handle menu button press events.
 * @param onSettingPressed Lambda function to handle settings button press events.
 */
@Composable
fun MenuAppBar(
    onMenuPressed: () -> Unit,
    onSettingPressed: () -> Unit,
) {
    AppBar(
        navigationIcon = {
            MenuButton(
                onClick = onMenuPressed // Handles menu button press.
            )
        },
        actions = {
            SettingsButton(
                onClick = onSettingPressed // Handles settings button press.
            )
        }
    )
}

/**
 * A composable for a menu button with an icon.
 *
 * @param onClick Lambda function to handle menu button click events.
 */
@Composable
fun MenuButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Menu, // Menu icon from material icons.
            contentDescription = "Toggle drawer" // Accessibility description.
        )
    }
}

/**
 * A composable for a back button with an animated gradient overlay.
 *
 * @param onClick Lambda function to handle back button click events.
 */
@Composable
fun BackButton(
    onClick: () -> Unit = {},
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Auto-mirrored back arrow icon.
            contentDescription = stringResource(R.string.back), // Accessibility description from resources.
            modifier = Modifier
                .graphicsLayer(alpha = 0.99f) // Adjusts alpha for rendering.
                .drawWithCache {
                    onDrawWithContent {
                        drawContent() // Draws the back button icon.
                        drawRect(blueYellowGradient, blendMode = BlendMode.SrcAtop) // Adds a gradient overlay.
                    }
                },
        )
    }
}

/**
 * A composable for a settings button with an icon.
 *
 * @param onClick Lambda function to handle settings button click events.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsButton(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Settings, // Settings icon from material icons.
            contentDescription = stringResource(R.string.settings) // Accessibility description from resources.
        )
    }
}

/**
 * Preview function showcasing different AppBar configurations.
 */
@Composable
fun AppBarPreview() {
    BackAppBar(
        onBackPressed = {
            // Handle back press
        }
    )

    MenuAppBar(
        onMenuPressed = {
            // Handle menu press
        },
        onSettingPressed = {
            // Handle settings press
        }
    )
}