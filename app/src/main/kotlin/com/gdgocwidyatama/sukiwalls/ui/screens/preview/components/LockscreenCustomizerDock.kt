package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gdgocwidyatama.sukiwalls.R
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenClockColor
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenClockStyle
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenConfig
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded

private enum class CustomizerTab { WALLPAPER, CLOCK, COLOR, DEPTH }

@Composable
fun LockscreenCustomizerDock(
    config: LockscreenConfig,
    isCarouselActive: Boolean,
    onToggleCarousel: () -> Unit,
    onConfigChange: (LockscreenConfig) -> Unit,
    onDoneClick: () -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var activeTab by remember { mutableStateOf(if (isCarouselActive) CustomizerTab.WALLPAPER else CustomizerTab.CLOCK) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        shadowElevation = 12.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                FilterChip(
                    selected = activeTab == CustomizerTab.WALLPAPER,
                    onClick = { activeTab = CustomizerTab.WALLPAPER },
                    label = { Text("Wallpaper", fontFamily = GoogleSansRounded) },
                )
                FilterChip(
                    selected = activeTab == CustomizerTab.CLOCK,
                    onClick = { activeTab = CustomizerTab.CLOCK },
                    label = { Text(stringResource(R.string.lockscreen_tab_clock), fontFamily = GoogleSansRounded) },
                )
                FilterChip(
                    selected = activeTab == CustomizerTab.COLOR,
                    onClick = { activeTab = CustomizerTab.COLOR },
                    label = { Text(stringResource(R.string.lockscreen_tab_color), fontFamily = GoogleSansRounded) },
                )
                FilterChip(
                    selected = activeTab == CustomizerTab.DEPTH,
                    onClick = { activeTab = CustomizerTab.DEPTH },
                    label = { Text(stringResource(R.string.lockscreen_tab_depth), fontFamily = GoogleSansRounded) },
                )
            }

            when (activeTab) {
                CustomizerTab.WALLPAPER -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        FilterChip(
                            selected = isCarouselActive,
                            onClick = onToggleCarousel,
                            label = {
                                Text(
                                    if (isCarouselActive) "Tutup Carousel (Terkunci)" else "Scroll ke Atas / Buka Carousel",
                                    fontFamily = GoogleSansRounded,
                                )
                            },
                        )
                    }
                }
                CustomizerTab.CLOCK -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        LockscreenClockStyle.entries.forEach { style ->
                            FilterChip(
                                selected = config.clockStyle == style,
                                onClick = { onConfigChange(config.copy(clockStyle = style)) },
                                label = { Text(stringResource(style.titleRes), fontFamily = GoogleSansRounded) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
                CustomizerTab.COLOR -> {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        LockscreenClockColor.entries.forEach { colorOption ->
                            val isSelected = config.clockColor == colorOption
                            val swatchColor = colorOption.color ?: MaterialTheme.colorScheme.primary
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(swatchColor)
                                    .border(
                                        width = if (isSelected) 3.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outlineVariant,
                                        shape = CircleShape,
                                    )
                                    .clickable { onConfigChange(config.copy(clockColor = colorOption)) },
                            )
                        }
                    }
                }
                CustomizerTab.DEPTH -> {
                    FilterChip(
                        selected = config.isDepthEnabled,
                        onClick = { onConfigChange(config.copy(isDepthEnabled = !config.isDepthEnabled)) },
                        label = { Text(if (config.isDepthEnabled) "Depth Effect: On" else "Depth Effect: Off") },
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Button(
                    onClick = onDoneClick,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(stringResource(R.string.lockscreen_action_done), color = MaterialTheme.colorScheme.onSurface)
                }
                Button(
                    onClick = onApplyClick,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(stringResource(R.string.action_apply), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}
