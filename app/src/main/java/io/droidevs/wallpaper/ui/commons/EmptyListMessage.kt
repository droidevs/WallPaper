package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * A styled, reusable composable for displaying an empty list message.
 *
 * @param title The main message to display (e.g., "No items found").
 * @param modifier The modifier to be applied to the layout.
 * @param subtitle An optional secondary message for more context.
 * @param icon The icon to display above the text. Defaults to a generic inbox icon.
 * @param actionText An optional text for a call-to-action button.
 * @param onActionClick An optional lambda to be invoked when the action button is clicked.
 */
@Composable
fun EmptyListMessage(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    icon: ImageVector,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Large, faded icon
            Icon(
                imageVector = icon,
                contentDescription = title, // Important for accessibility
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Title text
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Optional subtitle text
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Optional action button
            if (actionText != null && onActionClick != null) {
                Spacer(modifier = Modifier.height(32.dp))
                OutlinedButton(onClick = onActionClick) {
                    Text(text = actionText)
                }
            }
        }
    }
}