package io.droidevs.wallpaper.ui.dialogs.settings

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SureDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val scale = remember { Animatable(0.8f) }

    // Animate the dialog appearance
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)) // Dim background
            .clickable(onClick = onDismiss, indication = null, interactionSource = remember { MutableInteractionSource() }),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .scale(scale.value)
                .padding(24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.95f) // Glassmorphic effect
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Warning",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(48.dp)
                        .bounceEffect() // Custom bounce animation
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "No")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("No")
                    }

                    Button(
                        onClick = { onConfirm() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Yes")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Yes")
                    }
                }
            }
        }
    }
}

// Custom animation modifier for a subtle bounce effect
@Composable
fun Modifier.bounceEffect(): Modifier = this.then(
    Modifier.graphicsLayer {
        scaleX = 1.1f
        scaleY = 1.1f
    }.animateContentSize()
)
