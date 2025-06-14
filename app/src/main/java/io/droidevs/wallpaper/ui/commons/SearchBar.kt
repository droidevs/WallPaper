package io.droidevs.wallpaper.ui.commons
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search...",
    searchQuery: String = "",
    isActive: Boolean = true,
    onBackPressed: () -> Unit = {},
    onAction: (SearchBarAction) -> Unit = {}
) {

    val alpha by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.8f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha), RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon")
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = searchQuery,
                onValueChange = { onAction(SearchBarAction.QueryChanged(it)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onAction(SearchBarAction.Search) }),
                modifier = Modifier
                    .weight(1f)
                    .alpha(alpha),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = hint,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                    innerTextField()
                }
            )

            AnimatedVisibility(visible = searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = { onAction(SearchBarAction.Clear) }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Search")
                }
            }
        }
    }
}


sealed class SearchBarAction {
    data class QueryChanged(val query: String) : SearchBarAction()
    data object Search : SearchBarAction()
    data object Clear : SearchBarAction()
    data object Back : SearchBarAction()

}
