package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgocwidyatama.sukiwalls.R
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenClockColor
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenClockStyle
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenConfig
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Text

private enum class CustomizerTab { STYLE, COLOR, DEPTH }

@Composable
fun LockscreenCustomizerDock(
    config: LockscreenConfig,
    onConfigChange: (LockscreenConfig) -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var activeTab by remember { mutableStateOf(CustomizerTab.STYLE) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        color = Color(0xFF1E1E22),
        shadowElevation = 16.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Drag handle
            Box(
                modifier = Modifier
                    .size(width = 38.dp, height = 4.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f)),
            )

            // Title
            Text(
                text = stringResource(R.string.lockscreen_customize_title),
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.White,
            )

            // Category Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                FilterChip(
                    selected = activeTab == CustomizerTab.STYLE,
                    onClick = { activeTab = CustomizerTab.STYLE },
                    label = { Text(stringResource(R.string.lockscreen_tab_style), fontFamily = GoogleSansRounded) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF2979FF),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFF2C2C32),
                        labelColor = Color.White.copy(alpha = 0.7f),
                    ),
                )
                FilterChip(
                    selected = activeTab == CustomizerTab.COLOR,
                    onClick = { activeTab = CustomizerTab.COLOR },
                    label = { Text(stringResource(R.string.lockscreen_tab_color), fontFamily = GoogleSansRounded) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF2979FF),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFF2C2C32),
                        labelColor = Color.White.copy(alpha = 0.7f),
                    ),
                )
                FilterChip(
                    selected = activeTab == CustomizerTab.DEPTH,
                    onClick = { activeTab = CustomizerTab.DEPTH },
                    label = { Text(stringResource(R.string.lockscreen_tab_depth), fontFamily = GoogleSansRounded) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF2979FF),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFF2C2C32),
                        labelColor = Color.White.copy(alpha = 0.7f),
                    ),
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            when (activeTab) {
                CustomizerTab.STYLE -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        LockscreenClockStyle.entries.forEach { style ->
                            val isSelected = config.clockStyle == style
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (isSelected) Color(0xFF2979FF) else Color(0xFF2A2A30))
                                    .clickable { onConfigChange(config.copy(clockStyle = style)) }
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = style.displayName,
                                    fontFamily = GoogleSansRounded,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp,
                                    color = Color.White,
                                )
                            }
                        }
                    }
                }

                CustomizerTab.COLOR -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        LockscreenClockColor.entries.forEach { colorOption ->
                            val isSelected = config.clockColor == colorOption
                            val swatchColor = colorOption.color ?: MaterialTheme.colorScheme.primary
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(swatchColor)
                                    .border(
                                        width = if (isSelected) 3.dp else 1.dp,
                                        color = if (isSelected) Color.White else Color.White.copy(alpha = 0.2f),
                                        shape = CircleShape,
                                    )
                                    .clickable { onConfigChange(config.copy(clockColor = colorOption)) },
                            )
                        }
                    }
                }

                CustomizerTab.DEPTH -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (config.isDepthEnabled) Color(0xFF2979FF) else Color(0xFF2A2A30))
                                .clickable { onConfigChange(config.copy(isDepthEnabled = !config.isDepthEnabled)) }
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = if (config.isDepthEnabled) "Efek Kedalaman: Aktif" else "Efek Kedalaman: Nonaktif",
                                fontFamily = GoogleSansRounded,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Done Button with Miuix
            Button(
                onClick = onDoneClick,
                colors = ButtonDefaults.buttonColors(color = Color(0xFF2E2E34)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(
                    text = stringResource(R.string.lockscreen_action_done),
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White,
                )
            }
        }
    }
}
