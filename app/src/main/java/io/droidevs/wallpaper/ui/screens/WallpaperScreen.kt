package io.droidevs.wallpaper.ui.screens

import androidx.compose.runtime.Composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.domain.model.Screen
import io.droidevs.wallpaper.ui.dialogs.screen.ScreenSelectionDialog


@Composable
fun WallpaperScreen(wallpaper: LocalWallpaper, onApply: (Screen) -> Unit, onCustomize: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    var launchScreenDialog = remember { mutableStateOf(false) }

    // Custom animation for button scaling
    val buttonScale by animateFloatAsState(
        targetValue = if (scale > 1f) 1.2f else 1f,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = maxOf(1f, minOf(scale * zoom, 5f))
                    offsetX = maxOf(
                        -size.width * (scale - 1) / 2,
                        minOf(offsetX + pan.x, size.width * (scale - 1) / 2)
                    )
                    offsetY = maxOf(
                        -size.height * (scale - 1) / 2,
                        minOf(offsetY + pan.y, size.height * (scale - 1) / 2)
                    )
                }
            }
    ) {
        val painter = rememberAsyncImagePainter(
            model = wallpaper.filePath
        )
        // Wallpaper Image
        Image(
            painter = painter,
            contentDescription = "Wallpaper",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                ),
            contentScale = ContentScale.Crop
        )

        // Action Buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Apply Button
            Button(
                onClick = {
                    launchScreenDialog.value = true
                },
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = buttonScale
                        scaleY = buttonScale
                    }
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Apply")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Apply Wallpaper")
            }

            // Customize Button
            Button(
                onClick = onCustomize,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = buttonScale
                        scaleY = buttonScale
                    }
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Customize")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Customize Wallpaper")
            }
        }

        if (launchScreenDialog.value){
            ScreenSelectionDialog(
                onDismiss = {
                    launchScreenDialog.value = false
                },
                onOptionSelected = {
                    onApply.invoke(it)
                }
            )
        }
    }
}