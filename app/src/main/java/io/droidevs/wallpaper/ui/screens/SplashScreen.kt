package io.droidevs.wallpaper.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import io.droidevs.wallpaper.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    navigateToApp: () -> Unit) {
    val brandColor = MaterialTheme.colorScheme.background
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec =
            tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                },
            ),
        )
        delay(1000)

        navigateToApp()

    }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(brandColor),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.scale(scale.value),
            painter = painterResource(id = R.mipmap.ic_launcher),
            contentDescription = "app icon",
        )
    }
}