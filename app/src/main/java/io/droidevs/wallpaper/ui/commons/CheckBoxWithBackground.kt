package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * A helper composable to draw a nice background behind the checkbox
 * so it's visible on any image.
 */
@Composable
fun SelectCheckboxWithBackground(isSelected: Boolean, onToggle: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
    ) {
        Checkbox(
            checked = isSelected,
            // The parent `pointerInput` already handles the toggle, but we can also
            // allow direct clicks on the checkbox itself.
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}