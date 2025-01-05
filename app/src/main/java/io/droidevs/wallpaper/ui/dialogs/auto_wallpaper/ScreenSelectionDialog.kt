package io.droidevs.wallpaper.ui.dialogs.auto_wallpaper

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.infrastructure.datastore.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenSelectionDialog(
    onDismiss: () -> Unit,
    selectedOption : Screen,
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
            SingleChoiceSegmentedButtonRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(start = 16.dp, end = 16.dp))
            ) {
                SegmentedButton(
                    selected = selectedOption == Screen.Home,
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                    modifier = Modifier.testTag("home_button"),
                    onClick = {
                        onOptionSelected.invoke(Screen.Home)
                    }
                ) {
                    Text(stringResource(R.string.home_screen_btn))
                }
                SegmentedButton(
                    selected = selectedOption == Screen.Lock,
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                    modifier = Modifier.testTag("lock_button"),
                    onClick = {
                        onOptionSelected.invoke(Screen.Lock)
                    }
                ) {
                    Text(text = stringResource(R.string.lock_screen_btn))
                }
                SegmentedButton(
                    selected = selectedOption == Screen.Both,
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                    modifier = Modifier.testTag("lock_button"),
                    onClick = {
                        onOptionSelected.invoke(Screen.Both)
                    }
                ) {
                    Text(stringResource(R.string.both_screen_btn))
                }
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