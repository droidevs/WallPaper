package io.droidevs.wallpaper.ui.commons

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun FilterRowLayout(
    filterCount: Int,
    selectedFilterIndex: Int,
    onFilterSelected: (FilterItem) -> Unit,
    filterItem: (Int) -> FilterItem,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
){

    ScrollableTabRow(
        selectedTabIndex = selectedFilterIndex,
        indicator = {
            TabRowIndicator(it,selectedFilterIndex)
        }
    ) {
        (1..filterCount).forEach { index ->
            var tab = filterItem(index)
            Tab(
                selected = selectedFilterIndex == index,
                onClick = {
                    onFilterSelected(tab)
                },
                text = { Text(tab.title) }
            )
        }
    }
}



data class FilterItem(
    val id : Int,
    val title: String,
    val icon: ImageVector
)