package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgocwidyatama.sukiwalls.R
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenConfig
import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded
import com.gdgocwidyatama.sukiwalls.ui.components.NetworkImage
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Text

@Composable
fun LockscreenCanvasCard(
    wallpaper: Wallpaper,
    config: LockscreenConfig,
    isActiveCard: Boolean,
    onCustomizeClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val canvasShape = RoundedCornerShape(34.dp)
    val borderColor = if (isActiveCard) {
        Color.White.copy(alpha = 0.24f)
    } else {
        Color.White.copy(alpha = 0.08f)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .shadow(
                elevation = if (isActiveCard) 20.dp else 8.dp,
                shape = canvasShape,
                spotColor = Color.Black.copy(alpha = 0.6f),
            )
            .clip(canvasShape)
            .border(BorderStroke(1.2.dp, borderColor), canvasShape)
            .background(Color.Black)
            .pointerInput(isActiveCard) {
                detectTapGestures(
                    onTap = {
                        if (isActiveCard) {
                            onCustomizeClick()
                        } else {
                            onCardClick()
                        }
                    },
                    onLongPress = {
                        onCustomizeClick()
                    },
                )
            },
    ) {
        // Wallpaper Image
        NetworkImage(
            url = wallpaper.imageUrl,
            drawableResId = wallpaper.drawableResId,
            contentDescription = wallpaper.title,
            modifier = Modifier.fillMaxSize(),
        )

        // Depth & Atmosphere Gradient
        if (config.isDepthEnabled) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.40f), Color.Transparent),
                        ),
                    ),
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.50f)),
                        ),
                    ),
            )
        }

        // Lockscreen Overlay Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            // Clock Widget
            LockscreenClockWidget(
                style = config.clockStyle,
                clockColor = config.clockColor,
            )

            Spacer(modifier = Modifier.weight(1f))

            // "Kustomisasi" Pill Button (Shown on active card like HyperOS)
            if (isActiveCard) {
                Button(
                    onClick = onCustomizeClick,
                    colors = ButtonDefaults.buttonColors(
                        color = Color(0x73292C38),
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.action_customize),
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = Color.White,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
