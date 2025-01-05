package io.droidevs.wallpaper.ui.bottomsheet.autoWallpaper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.infrastructure.datastore.TimeInterval
import io.droidevs.wallpaper.ui.bottomsheet.AdaptiveBottomSheet


@Composable
fun TimeSelectionBottomSheet(
    onDismiss: () -> Unit,
    selectedOption: TimeInterval,
    onTimeUpdated: (TimeInterval) -> Unit
){

    val options = listOf(
        stringResource(R.string.off) to TimeInterval.Off,
        stringResource(R.string.every_minute) to TimeInterval.EveryMinute,
        stringResource(R.string.every_5_minutes) to TimeInterval.Every5Minute,
        stringResource(R.string.every_15_minutes) to TimeInterval.Every15Minute,
        stringResource(R.string.every_30_minutes) to TimeInterval.Every30Minute,
        stringResource(R.string.every_hour) to TimeInterval.EveryHour,
        stringResource(R.string.every_3_hours) to TimeInterval.Every3Hour,
        stringResource(R.string.every_6_hours) to TimeInterval.Every6Hour,
        stringResource(R.string.every_12_hours) to TimeInterval.Every12Hour,
        stringResource(R.string.every_day) to TimeInterval.EveryDay,
        stringResource(R.string.every_3_days) to TimeInterval.Every3Days,
        stringResource(R.string.every_week) to TimeInterval.EveryWeek
    )


    AdaptiveBottomSheet(
        onDismissRequest = onDismiss
    ){
        Column {
            options.forEach { option ->
                Button(
                    onClick = {
                        onTimeUpdated.invoke(option.second)
                        onDismiss()
                    },
                    colors = when (selectedOption) {
                        option.second -> {
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        }

                        else -> {
                            ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = option.first,
                        color = when (selectedOption) {
                            option.second -> MaterialTheme.colorScheme.onPrimary
                            else -> MaterialTheme.colorScheme.onSurface
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}