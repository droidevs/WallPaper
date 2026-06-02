package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.domain.TopicOrderBy


@Composable
fun SortOrderSelector(
    currentSortOrder: TopicOrderBy,
    onSortOrderSelected: (TopicOrderBy) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier
                .height(40.dp)
                .padding(horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Sort: ${currentSortOrder.toDisplayName()}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Sort options"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(200.dp)
        ) {
            TopicOrderBy.values().forEach { sortOrder ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = sortOrder.toDisplayName(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onSortOrderSelected(sortOrder)
                        expanded = false
                    },
                    leadingIcon = {
                        if (sortOrder == currentSortOrder) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected"
                            )
                        }
                    }
                )
            }
        }
    }
}

// Extension function to display user-friendly names
fun TopicOrderBy.toDisplayName(): String {
    return when (this) {
        TopicOrderBy.FEATURED -> "Featured"
        TopicOrderBy.LATEST -> "Latest"
        TopicOrderBy.OLDEST -> "Oldest"
        TopicOrderBy.POSITION -> "Position"
    }
}