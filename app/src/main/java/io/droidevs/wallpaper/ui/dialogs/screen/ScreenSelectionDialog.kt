package io.droidevs.wallpaper.ui.dialogs.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.domain.model.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenSelectionDialog(
    onDismiss: () -> Unit,
    onOptionSelected: (Screen) -> Unit,
){

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = stringResource(R.string.auto_wallpaper_set_screen))
        },
        text = {
            Button(
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                modifier = Modifier.testTag("home_button"),
                onClick = {
                    onOptionSelected.invoke(Screen.HOME)
                    onDismiss.invoke()
                }
            ) {
                Text(stringResource(R.string.home_screen_btn))
            }
            Button(
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                modifier = Modifier.testTag("lock_button"),
                onClick = {
                    onOptionSelected.invoke(Screen.LOCK)
                    onDismiss.invoke()
                }
            ) {
                Text(text = stringResource(R.string.lock_screen_btn))
            }
            Button(
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                modifier = Modifier.testTag("lock_button"),
                onClick = {
                    onOptionSelected.invoke(Screen.BOTH)
                    onDismiss.invoke()
                }
            ) {
                Text(stringResource(R.string.both_screen_btn))
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = stringResource(R.string.apply))
            }
        },
        properties = DialogProperties(dismissOnClickOutside = true)
    )
}