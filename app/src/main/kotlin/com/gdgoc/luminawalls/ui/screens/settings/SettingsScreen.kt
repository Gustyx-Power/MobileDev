package com.gdgoc.luminawalls.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgoc.luminawalls.data.repository.AppThemeMode
import com.gdgoc.luminawalls.theme.GoogleSansRounded
import com.gdgoc.luminawalls.ui.components.LuminaListItem
import com.gdgoc.luminawalls.ui.components.LuminaScreenScaffold

/**
 * Layar Pengaturan: Konfigurasi tema (Monet / Dark / Light).
 */
@Composable
fun SettingsScreen(
    currentThemeMode: AppThemeMode,
    onThemeModeSelected: (AppThemeMode) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LuminaScreenScaffold(
        title = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.settings_title),
        subtitle = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.settings_subtitle),
        leadingAction = {

            // TODO: [Session 4] Tulis FilledIconButton kembali (onBackClick) di bawah sini:



        },
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 22.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            // Section: APPEARANCE
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.settings_section_appearance),
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = GoogleSansRounded,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 12.dp, bottom = 2.dp),
                        letterSpacing = 1.2.sp,
                    )

                    // TODO: [Session 3] Tulis opsi pemilihan tema (System, Light, Dark) menggunakan ThemeOptionItem di bawah sini:



                }
            }
        }
    }
}

@Composable
fun ThemeOptionItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    shape: androidx.compose.ui.graphics.Shape,
) {
    val containerColor = if (selected) {
        MaterialTheme.colorScheme.surfaceContainerHighest
    } else {
        MaterialTheme.colorScheme.surfaceContainerLow
    }

    val iconBgColor = if (selected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
    } else {
        MaterialTheme.colorScheme.surfaceContainerHighest
    }

    val iconTint = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    LuminaListItem(
        title = title,
        subtitle = subtitle,
        shape = shape,
        containerColor = containerColor,
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(iconBgColor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp),
                )
            }
        },
        trailingContent = if (selected) {
            {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp),
                )
            }
        } else null,
        onClick = onClick,
    )
}
