package io.droidevs.wallpaper.ui.commons

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.rememberCoroutineScope
import io.droidevs.wallpaper.ui.model.FilterItem
import kotlinx.coroutines.CoroutineScope


@Composable
fun FilterRowLayout(
    items: List<FilterItem>,
    selectedFilterIndex: Int,
    onFilterSelected: (FilterItem) -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
){

    ScrollableTabRow(
        selectedTabIndex = selectedFilterIndex,
        indicator = {
            TabRowIndicator(it,selectedFilterIndex)
        }
    ) {
        items.forEachIndexed { index, filter->
            Tab(
                selected = selectedFilterIndex == index,
                onClick = {
                    onFilterSelected(filter)
                },
                text = { Text(filter.title) }
            )
        }
    }
}


