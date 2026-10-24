package com.gdgoc.luminawalls.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.People
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gdgoc.luminawalls.theme.GoogleSansRounded

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.gdgoc.luminawalls.R

/**
 * Floating Navigation Bar: Komponen navigasi melayang berbentuk pill di bawah layar.
 */
enum class LuminaTab(
    @param:StringRes val titleRes: Int,
    val icon: ImageVector,
) {
    HOME(R.string.tab_home, Icons.Rounded.Home),
    CONTRIBUTORS(R.string.tab_about, Icons.Rounded.People),
}

@Composable
fun FloatingNavBar(
    selectedTab: LuminaTab,
    onTabSelected: (LuminaTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.95f),
            tonalElevation = 8.dp,
            shadowElevation = 10.dp,
            modifier = Modifier.padding(horizontal = 24.dp),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LuminaTab.entries.forEach { tab ->
                    val isSelected = tab == selectedTab
                    val scale by animateFloatAsState(
                        targetValue = if (isSelected) 1.08f else 1.0f,
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                        label = "navTabScale",
                    )
                    val iconTint by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        label = "navTabTint",
                    )
                    val backgroundAlpha by animateFloatAsState(
                        targetValue = if (isSelected) 1f else 0f,
                        label = "navTabBgAlpha",
                    )

                    val tabTitle = stringResource(tab.titleRes)

                    Box(
                        modifier = Modifier
                            .scale(scale)
                            .clip(CircleShape)
                            .bouncyClickable(onClick = { onTabSelected(tab) })
                            .padding(horizontal = 4.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = backgroundAlpha))
                                .animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMedium,
                                    ),
                                )
                                .padding(
                                    horizontal = if (isSelected) 16.dp else 12.dp,
                                    vertical = 8.dp,
                                ),
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tabTitle,
                                tint = iconTint,
                                modifier = Modifier.size(22.dp),
                            )
                            androidx.compose.animation.AnimatedVisibility(
                                visible = isSelected,
                                enter = androidx.compose.animation.fadeIn(androidx.compose.animation.core.tween(180)) +
                                    androidx.compose.animation.expandHorizontally(
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMedium,
                                        ),
                                    ),
                                exit = androidx.compose.animation.fadeOut(androidx.compose.animation.core.tween(150)) +
                                    androidx.compose.animation.shrinkHorizontally(
                                        animationSpec = androidx.compose.animation.core.tween(150),
                                    ),
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(8.dp))
                                    Text(
                                        text = tabTitle,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontFamily = GoogleSansRounded,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        maxLines = 1,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
