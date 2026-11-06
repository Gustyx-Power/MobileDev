package com.gdgocwidyatama.sukiwalls.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgocwidyatama.sukiwalls.R
import com.gdgocwidyatama.sukiwalls.data.repository.AppThemeMode
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded
import com.gdgocwidyatama.sukiwalls.ui.components.SukiScreenScaffold
import com.gdgocwidyatama.sukiwalls.ui.screens.settings.components.ThemeOptionItem

@Composable
fun SettingsScreen(
    currentThemeMode: AppThemeMode,
    onThemeModeSelected: (AppThemeMode) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SukiScreenScaffold(
        title = stringResource(R.string.settings_title),
        subtitle = stringResource(R.string.settings_subtitle),
        leadingAction = {
            FilledIconButton(
                onClick = onBackClick,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier.size(40.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.action_back),
                    modifier = Modifier.size(20.dp),
                )
            }
        },
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 22.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = stringResource(R.string.settings_section_appearance),
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = GoogleSansRounded,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 12.dp, bottom = 2.dp),
                        letterSpacing = 1.2.sp,
                    )

                    ThemeOptionItem(
                        title = stringResource(R.string.settings_theme_system),
                        subtitle = stringResource(R.string.settings_theme_system_desc),
                        icon = Icons.Rounded.PhoneAndroid,
                        selected = currentThemeMode == AppThemeMode.FOLLOW_SYSTEM,
                        onClick = { onThemeModeSelected(AppThemeMode.FOLLOW_SYSTEM) },
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 6.dp, bottomEnd = 6.dp),
                    )

                    ThemeOptionItem(
                        title = stringResource(R.string.settings_theme_light),
                        subtitle = stringResource(R.string.settings_theme_light_desc),
                        icon = Icons.Rounded.LightMode,
                        selected = currentThemeMode == AppThemeMode.LIGHT,
                        onClick = { onThemeModeSelected(AppThemeMode.LIGHT) },
                        shape = RoundedCornerShape(6.dp),
                    )

                    ThemeOptionItem(
                        title = stringResource(R.string.settings_theme_dark),
                        subtitle = stringResource(R.string.settings_theme_dark_desc),
                        icon = Icons.Rounded.DarkMode,
                        selected = currentThemeMode == AppThemeMode.DARK,
                        onClick = { onThemeModeSelected(AppThemeMode.DARK) },
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp, topStart = 6.dp, topEnd = 6.dp),
                    )
                }
            }
        }
    }
}
