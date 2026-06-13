package io.droidevs.wallpaper.ui.commons

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowWarningDialog(title: String, warning: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = warning)
        },
        confirmButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(text = androidx.compose.ui.res.stringResource(android.R.string.ok))
            }
        }
    )
}