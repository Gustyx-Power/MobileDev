package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenClockColor
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenClockStyle
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansDisplay
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LockscreenClockWidget(
    style: LockscreenClockStyle,
    clockColor: LockscreenClockColor,
    modifier: Modifier = Modifier,
) {
    var currentTime by remember { mutableStateOf(Date()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Date()
            delay(1000)
        }
    }

    val resolvedColor = clockColor.color ?: MaterialTheme.colorScheme.primary

    val hourFormat = remember { SimpleDateFormat("HH", Locale.getDefault()) }
    val minuteFormat = remember { SimpleDateFormat("mm", Locale.getDefault()) }
    val dateFormat = remember { SimpleDateFormat("EEE, d MMM", Locale.getDefault()) }

    val hours = hourFormat.format(currentTime)
    val minutes = minuteFormat.format(currentTime)
    val dateText = dateFormat.format(currentTime)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (style) {
            LockscreenClockStyle.HYPEROS_STACKED -> {
                Text(
                    text = dateText,
                    style = MaterialTheme.typography.labelMedium,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.SemiBold,
                    color = resolvedColor.copy(alpha = 0.88f),
                    modifier = Modifier.padding(bottom = 2.dp),
                )
                Text(
                    text = hours,
                    fontSize = 72.sp,
                    fontFamily = GoogleSansDisplay,
                    fontWeight = FontWeight.Black,
                    color = resolvedColor,
                    lineHeight = 64.sp,
                    letterSpacing = (-2).sp,
                )
                Text(
                    text = minutes,
                    fontSize = 72.sp,
                    fontFamily = GoogleSansDisplay,
                    fontWeight = FontWeight.Black,
                    color = resolvedColor,
                    lineHeight = 64.sp,
                    letterSpacing = (-2).sp,
                )
            }

            LockscreenClockStyle.ORIGIN_SPLIT -> {
                Text(
                    text = "$dateText · 28°C",
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Bold,
                    color = resolvedColor.copy(alpha = 0.90f),
                    modifier = Modifier.padding(bottom = 6.dp),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = hours,
                        fontSize = 58.sp,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Bold,
                        color = resolvedColor,
                        lineHeight = 58.sp,
                    )
                    Text(
                        text = ":",
                        fontSize = 52.sp,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Light,
                        color = resolvedColor.copy(alpha = 0.7f),
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                    Text(
                        text = minutes,
                        fontSize = 58.sp,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Bold,
                        color = resolvedColor,
                        lineHeight = 58.sp,
                    )
                }
            }

            LockscreenClockStyle.NEO_SERIF -> {
                Text(
                    text = "$hours:$minutes",
                    fontSize = 62.sp,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Light,
                    color = resolvedColor,
                    lineHeight = 62.sp,
                    letterSpacing = 2.sp,
                )
                Text(
                    text = dateText.uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.5.sp,
                    color = resolvedColor.copy(alpha = 0.85f),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
    }
}
