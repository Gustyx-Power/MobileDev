package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenClockStyle
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansDisplay
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HyperOSBottomPeekCard(
    nextStyle: LockscreenClockStyle,
    onPeekClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentTime by remember { mutableStateOf(Date()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Date()
            delay(1000)
        }
    }

    val hourFormat = remember { SimpleDateFormat("HH", Locale.getDefault()) }
    val minuteFormat = remember { SimpleDateFormat("mm", Locale.getDefault()) }
    val dateFormat = remember { SimpleDateFormat("MMM dd, EEE", Locale.US) }

    val hours = hourFormat.format(currentTime)
    val minutes = minuteFormat.format(currentTime)
    val dateText = dateFormat.format(currentTime)

    val cardShape = RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp)

    Box(
        modifier = modifier
            .width(220.dp)
            .height(72.dp)
            .clip(cardShape)
            .border(
                BorderStroke(1.2.dp, Color.White.copy(alpha = 0.22f)),
                cardShape,
            )
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E88E5),
                        Color(0xFF1565C0),
                    ),
                ),
            )
            .clickable { onPeekClick() }
            .padding(top = 10.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = dateText,
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.90f),
            )
            Text(
                text = "$hours:$minutes",
                fontFamily = when (nextStyle) {
                    LockscreenClockStyle.KLASIK -> FontFamily.Serif
                    LockscreenClockStyle.ROMBO -> GoogleSansDisplay
                    else -> GoogleSansRounded
                },
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                lineHeight = 38.sp,
                color = Color.White,
            )
        }
    }
}

