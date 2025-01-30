package io.droidevs.wallpaper.ui.commons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * A composable function for rendering a customizable floating action button (FAB) with animated visibility.
 *
 * @param showFAB Determines if the FAB should be visible.
 * @param text The text displayed on the FAB when expanded.
 * @param icon The icon displayed on the FAB.
 * @param fabColor The background color of the FAB.
 * @param isFabExpanded Determines if the FAB should display its text (expanded state).
 * @param onClick Callback for handling FAB click events.
 * @param contentPadding Padding applied around the FAB.
 */
@Composable
fun AppFloatingActionButton(
    modifier: Modifier = Modifier, // Modifier for the FAB.
    showFAB: Boolean = true, // Visibility of the FAB.
    text: String = "", // Text displayed on the FAB when expanded.
    icon: ImageVector = Icons.Default.MoreVert, // Default icon for the FAB.
    fabColor: Color = MaterialTheme.colorScheme.primary, // Background color of the FAB.
    isFabExpanded: Boolean = false, // Whether the FAB is in an expanded state.
    onClick: () -> Unit, // Action to perform when the FAB is clicked.
    contentPadding: Dp = 16.dp, // Padding around the FAB.
) {

    val animatedFabColor by animateColorAsState(
        targetValue = if (isFabExpanded) MaterialTheme.colorScheme.secondary else fabColor,
        animationSpec = tween(300)
    )

    val scale by animateFloatAsState(
        targetValue = if (isFabExpanded) 1.1f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 300f)
    )

    val shadow by animateDpAsState(
        targetValue = if (isFabExpanded) 12.dp else 6.dp,
        animationSpec = tween(300)
    )

    AnimatedVisibility(
        visible = showFAB,
        enter = scaleIn(initialScale = 0.8f) + fadeIn(),
        exit = scaleOut(targetScale = 0.8f) + fadeOut()
    ) {
        ExtendedFloatingActionButton(
            modifier = modifier
                .padding(contentPadding)
                .scale(scale)
                .shadow(shadow, shape = CircleShape)
                .clip(CircleShape)
                .background(animatedFabColor.copy(alpha = 0.85f))
                .blur(8.dp), // Glassmorphism effect
            containerColor = animatedFabColor,
            onClick = {
                onClick()
            },
            expanded = isFabExpanded,
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            },
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
    }
}




// Define a data class to hold the props
data class FloatingActionButtonProps(
    val showFAB: Boolean,
    val text: String,
    val icon: ImageVector,
    val fabColor: Color,
    val isFabExpanded: Boolean,
    val contentPadding: Float
)

// Implement a PreviewParameterProvider for FloatingActionButtonProps
class FloatingActionButtonPropsProvider :
    CollectionPreviewParameterProvider<FloatingActionButtonProps>(
        listOf(
            FloatingActionButtonProps(
                showFAB = true,
                text = "Add Wallpapers",
                icon = Icons.Default.Add,
                fabColor = Color.Green,
                isFabExpanded = false,
                contentPadding = 16f
            ),
            FloatingActionButtonProps(
                showFAB = true,
                text = "More Options",
                icon = Icons.Default.MoreVert,
                fabColor = Color.Blue,
                isFabExpanded = true,
                contentPadding = 24f
            ),
            FloatingActionButtonProps(
                showFAB = false,
                text = "Hidden FAB",
                icon = Icons.Default.MoreVert,
                fabColor = Color.Red,
                isFabExpanded = false,
                contentPadding = 16f
            )
        )
    )

// Preview composable for AppFloatingActionButton
@Preview(showBackground = true)
@Composable
fun PreviewAppFloatingActionButton(
    @PreviewParameter(FloatingActionButtonPropsProvider::class) props: FloatingActionButtonProps
) {
    AppFloatingActionButton(
        showFAB = props.showFAB,
        text = props.text,
        icon = props.icon,
        fabColor = props.fabColor,
        isFabExpanded = props.isFabExpanded,
        onClick = {}, // No-op click action for preview
        contentPadding = props.contentPadding.dp
    )
}