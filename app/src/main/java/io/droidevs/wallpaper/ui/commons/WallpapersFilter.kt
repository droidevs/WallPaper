package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.data.util.SortOrder
import io.droidevs.wallpaper.data.util.SortType


@Composable
fun WallpapersFilter(
    onApplyFilter: (sort: SortType, order: SortOrder) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(SortType.DATE) }
    var selectedOrder by remember { mutableStateOf(SortOrder.DESC) }

    // Filters and Order options
    val filters = listOf(SortType.NAME, SortType.DATE, SortType.SIZE,SortType.HEIGHT, SortType.WIDTH)
    val orders = listOf(SortOrder.ASC, SortOrder.DESC)

    // Filter Button
    IconButton (
        onClick = { showDialog = true },
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Filter,
            contentDescription = "Filter Wallpapers",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    // Filter Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Filter Wallpapers", style = MaterialTheme.typography.titleMedium)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Select Filter:")

                    filters.forEach { filter ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (filter == selectedFilter),
                                onClick = { selectedFilter = filter }
                            )
                            Text(text = filter, modifier = Modifier.padding(start = 8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Select Order:")

                    orders.forEach { order ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (order == selectedOrder),
                                onClick = { selectedOrder = order }
                            )
                            Text(text = order, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onApplyFilter(selectedFilter, selectedOrder)
                    showDialog = false
                }) {
                    Text(text = "Apply")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}
