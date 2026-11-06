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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
    compact: Boolean = false,
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
    val dateFormat = remember { SimpleDateFormat("MMM dd, EEE", Locale.US) }

    val hours = hourFormat.format(currentTime)
    val minutes = minuteFormat.format(currentTime)
    val dateText = dateFormat.format(currentTime)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = if (compact) 8.dp else 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (style) {
            LockscreenClockStyle.KLASIK -> {
                Text(
                    text = dateText,
                    style = if (compact) MaterialTheme.typography.labelSmall else MaterialTheme.typography.labelMedium,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Normal,
                    fontSize = if (compact) 10.sp else 13.sp,
                    color = resolvedColor.copy(alpha = 0.92f),
                    modifier = Modifier.padding(bottom = if (compact) 1.dp else 2.dp),
                )
                Text(
                    text = "$hours:$minutes",
                    fontSize = if (compact) 42.sp else 74.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = resolvedColor,
                    lineHeight = if (compact) 42.sp else 74.sp,
                    letterSpacing = (-1).sp,
                )
            }

            LockscreenClockStyle.ROMBO -> {
                Text(
                    text = dateText,
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = if (compact) 9.sp else 12.sp,
                    color = resolvedColor.copy(alpha = 0.88f),
                    modifier = Modifier.padding(bottom = 1.dp),
                )
                Text(
                    text = hours,
                    fontSize = if (compact) 36.sp else 66.sp,
                    fontFamily = GoogleSansDisplay,
                    fontWeight = FontWeight.Black,
                    color = resolvedColor,
                    lineHeight = if (compact) 32.sp else 58.sp,
                    letterSpacing = (-2).sp,
                )
                Text(
                    text = minutes,
                    fontSize = if (compact) 36.sp else 66.sp,
                    fontFamily = GoogleSansDisplay,
                    fontWeight = FontWeight.Black,
                    color = resolvedColor,
                    lineHeight = if (compact) 32.sp else 58.sp,
                    letterSpacing = (-2).sp,
                )
            }

            LockscreenClockStyle.MAJALAH -> {
                Text(
                    text = "$dateText · 28°C",
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (compact) 9.sp else 11.sp,
                    color = resolvedColor.copy(alpha = 0.90f),
                    modifier = Modifier.padding(bottom = 4.dp),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = hours,
                        fontSize = if (compact) 34.sp else 56.sp,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Bold,
                        color = resolvedColor,
                        lineHeight = if (compact) 34.sp else 56.sp,
                    )
                    Text(
                        text = ":",
                        fontSize = if (compact) 30.sp else 50.sp,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Light,
                        color = resolvedColor.copy(alpha = 0.7f),
                        modifier = Modifier.padding(horizontal = 2.dp),
                    )
                    Text(
                        text = minutes,
                        fontSize = if (compact) 34.sp else 56.sp,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Bold,
                        color = resolvedColor,
                        lineHeight = if (compact) 34.sp else 56.sp,
                    )
                }
            }

            LockscreenClockStyle.GEOMETRI -> {
                Text(
                    text = "$hours:$minutes",
                    fontSize = if (compact) 38.sp else 60.sp,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Light,
                    color = resolvedColor,
                    lineHeight = if (compact) 38.sp else 60.sp,
                    letterSpacing = 2.sp,
                )
                Text(
                    text = dateText.uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = if (compact) 9.sp else 11.sp,
                    letterSpacing = 1.5.sp,
                    color = resolvedColor.copy(alpha = 0.85f),
                    modifier = Modifier.padding(top = 2.dp),
                )
            }

            LockscreenClockStyle.MINIMALIS -> {
                Text(
                    text = "$hours:$minutes",
                    fontSize = if (compact) 40.sp else 68.sp,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.ExtraLight,
                    color = resolvedColor,
                    lineHeight = if (compact) 40.sp else 68.sp,
                    letterSpacing = 3.sp,
                )
                Text(
                    text = dateText,
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Normal,
                    fontSize = if (compact) 9.sp else 11.sp,
                    letterSpacing = 1.sp,
                    color = resolvedColor.copy(alpha = 0.80f),
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
        }
    }
}
