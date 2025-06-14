package io.droidevs.wallpaper.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.domain.model.SearchHistory
import io.droidevs.wallpaper.ui.commons.AppSearchBar
import io.droidevs.wallpaper.ui.commons.SearchBarAction
import io.droidevs.wallpaper.ui.model.SearchHistoryUi
import io.droidevs.wallpaper.ui.viewmodels.actions.SearchScreenAction
import io.droidevs.wallpaper.ui.viewmodels.state.SearchScreenState

@Composable
fun SearchScreen(
    state: SearchScreenState,
    onAction: (SearchScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSuggestions by remember { mutableStateOf(false) }
    val transition = updateTransition(showSuggestions, label = "suggestionsTransition")
    val suggestionAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 150) },
        label = "alpha"
    ) { if (it) 1f else 0f }
    val suggestionElevation by transition.animateDp(
        transitionSpec = { tween(durationMillis = 200) },
        label = "elevation"
    ) { if (it) 8.dp else 0.dp }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Your existing customized search bar (replace with your component)
            AppSearchBar(
                searchQuery = state.query,
                onAction = {
                    onAction(it.toSearchScreenAction())
                }
            )
            // Animated suggestions container
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Search results or empty state would go here
                if (state.query.isEmpty()) {
                    SearchHistorySection(
                        recentSearches = state.recentSuggestions,
                        onClick = {
                            onAction(SearchScreenAction.PickSuggestion(it))
                        }
                    ) // Your recent searches component
                } else {

                    AnimatedVisibility(
                        label = "",
                        visible = showSuggestions && state.suggestions.isNotEmpty(),
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .shadow(suggestionElevation, shape = MaterialTheme.shapes.medium),
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            tonalElevation = 2.dp
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 400.dp)
                            ) {
                                itemsIndexed(state.suggestions) { index, suggestion ->
                                    SuggestionItem(
                                        suggestion = suggestion,
                                        isLast = index == state.suggestions.lastIndex,
                                        onClick = {
                                            onAction(SearchScreenAction.PickSuggestion(suggestion))
                                            showSuggestions = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Empty results state
                    if (showSuggestions && state.suggestions.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No suggestions found",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SuggestionItem(
    suggestion: SearchHistoryUi,
    isLast: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent,
        shape = when {
            isLast -> MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(0.dp),
                topEnd = CornerSize(0.dp)
            )
            else -> MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(0.dp),
                topEnd = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp)
            )
        },
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = suggestion.query,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Select",
                tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun SearchHistorySection(
    recentSearches: List<SearchHistoryUi> = emptyList(),
    onClick: (SearchHistoryUi) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Recent Searches",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        recentSearches.forEach { item ->
            Text(
                text = item.query,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(item)
                    }
                    .padding(vertical = 12.dp)
            )
            Divider(modifier = Modifier.padding(start = 8.dp))
        }
    }
}

fun SearchBarAction.toSearchScreenAction(): SearchScreenAction {
    return when (this) {
        is SearchBarAction.QueryChanged -> SearchScreenAction.UpdateQuery(this.query)
        is SearchBarAction.Search -> SearchScreenAction.Search
        is SearchBarAction.Clear -> SearchScreenAction.ClearSearch
        is SearchBarAction.Back -> SearchScreenAction.OnBackPressed
        else -> {
            throw IllegalArgumentException("Unknown SearchBarAction: $this")
        }
    } as SearchScreenAction
}

