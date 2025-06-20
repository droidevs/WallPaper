package io.droidevs.wallpaper.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.ui.commons.FilterRowLayout
import io.droidevs.wallpaper.ui.model.FilterCategory
import io.droidevs.wallpaper.ui.model.FilterItem

/**
 * The main content for the filter bottom sheet.
 *
 * @param allCategories The complete list of all filter categories and their options.
 * @param initialSelections The map of currently applied filters.
 * @param defaultSelections The map of default filters to use for the Reset action.
 * @param onApplyClicked Callback invoked when the user presses "Apply", passing the new selections.
 * @param onCancelClicked Callback invoked when the user presses "Cancel".
 */
@Composable
fun FilterContent(
    allCategories: List<FilterCategory>,
    initialSelections: Map<FilterCategory, FilterItem>,
    defaultSelections: Map<FilterCategory, FilterItem>,
    onApplyClicked: (Map<FilterCategory, FilterItem>) -> Unit,
    onCancelClicked: () -> Unit
) {
    // THIS IS THE KEY: A temporary state that the user modifies.
    // It's initialized with the current filters from the parent.
    var temporarySelections by remember { mutableStateOf(initialSelections) }

    Column(
        modifier = Modifier.navigationBarsPadding() // Important for gesture navigation
    ) {
        // --- Scrollable Filter Rows ---
        Column(
            modifier = Modifier
                .weight(1f) // Takes up all available space, pushing buttons to the bottom
                .verticalScroll(rememberScrollState())
        ) {
            allCategories.forEach { category ->
                // Display the category title
                Text(
                    text = category.title,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                FilterRowLayout(
                    items = category.filters,
                    selectedFilterIndex = temporarySelections[category]?.id ?: -1,
                    onFilterSelected = { selectedItem ->
                        // Update the temporary state map when an item is clicked
                        temporarySelections = temporarySelections + (category to selectedItem)
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- Action Buttons ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // The small "Reset" button, aligned to the end
            TextButton(
                onClick = { temporarySelections = defaultSelections },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Reset Filters")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Cancel Button
                OutlinedButton(
                    onClick = onCancelClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                // Apply Button
                Button(
                    onClick = { onApplyClicked(temporarySelections) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Apply")
                }
            }
        }
    }
}