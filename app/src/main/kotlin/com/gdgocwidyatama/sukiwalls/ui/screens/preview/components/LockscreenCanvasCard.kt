package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenConfig
import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper
import com.gdgocwidyatama.sukiwalls.ui.components.NetworkImage

@Composable
fun LockscreenCanvasCard(
    wallpaper: Wallpaper,
    config: LockscreenConfig,
    isEditMode: Boolean,
    onLongPress: () -> Unit,
    onTap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val canvasShape = RoundedCornerShape(32.dp)
    val borderColor = if (isEditMode) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(9f / 19.5f)
            .shadow(
                elevation = if (isEditMode) 24.dp else 16.dp,
                shape = canvasShape,
                spotColor = Color.Black.copy(alpha = 0.5f),
            )
            .clip(canvasShape)
            .border(BorderStroke(if (isEditMode) 2.5.dp else 1.5.dp, borderColor), canvasShape)
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress() },
                    onTap = { onTap() },
                )
            },
    ) {
        NetworkImage(
            url = wallpaper.imageUrl,
            contentDescription = wallpaper.title,
            modifier = Modifier.fillMaxSize(),
        )

        if (config.isDepthEnabled) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.55f), Color.Transparent),
                        ),
                    ),
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.60f)),
                        ),
                    ),
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LockscreenStatusHeader()
            Spacer(modifier = Modifier.height(16.dp))
            LockscreenClockWidget(
                style = config.clockStyle,
                clockColor = config.clockColor,
            )
            Spacer(modifier = Modifier.weight(1f))
            LockscreenQuickShortcuts()
        }
    }
}
