package com.gdgocwidyatama.sukiwalls.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

val SukiLightColorScheme = lightColorScheme(
    primary = SukiPrimaryLight,
    onPrimary = SukiOnPrimaryLight,
    primaryContainer = SukiPrimaryContainerLight,
    onPrimaryContainer = SukiOnPrimaryContainerLight,
    secondary = SukiSecondaryLight,
    onSecondary = SukiOnSecondaryLight,
    secondaryContainer = SukiSecondaryContainerLight,
    onSecondaryContainer = SukiOnSecondaryContainerLight,
    tertiary = SukiTertiaryLight,
    onTertiary = SukiOnTertiaryLight,
    tertiaryContainer = SukiTertiaryContainerLight,
    onTertiaryContainer = SukiOnTertiaryContainerLight,
    background = SukiBackgroundLight,
    onBackground = SukiOnBackgroundLight,
    surface = SukiSurfaceLight,
    onSurface = SukiOnSurfaceLight,
    surfaceVariant = SukiSurfaceVariantLight,
    onSurfaceVariant = SukiOnSurfaceVariantLight,
    surfaceContainerLow = SukiSurfaceContainerLowLight,
    surfaceContainer = SukiSurfaceContainerLight,
    surfaceContainerHigh = SukiSurfaceContainerHighLight,
    surfaceContainerHighest = SukiSurfaceContainerHighestLight,
)

val SukiDarkColorScheme = darkColorScheme(
    primary = SukiPrimaryDark,
    onPrimary = SukiOnPrimaryDark,
    primaryContainer = SukiPrimaryContainerDark,
    onPrimaryContainer = SukiOnPrimaryContainerDark,
    secondary = SukiSecondaryDark,
    onSecondary = SukiOnSecondaryDark,
    secondaryContainer = SukiSecondaryContainerDark,
    onSecondaryContainer = SukiOnSecondaryContainerDark,
    tertiary = SukiTertiaryDark,
    onTertiary = SukiOnTertiaryDark,
    tertiaryContainer = SukiTertiaryContainerDark,
    onTertiaryContainer = SukiOnTertiaryContainerDark,
    background = SukiBackgroundDark,
    onBackground = SukiOnBackgroundDark,
    surface = SukiSurfaceDark,
    onSurface = SukiOnSurfaceDark,
    surfaceVariant = SukiSurfaceVariantDark,
    onSurfaceVariant = SukiOnSurfaceVariantDark,
    surfaceContainerLow = SukiSurfaceContainerLowDark,
    surfaceContainer = SukiSurfaceContainerDark,
    surfaceContainerHigh = SukiSurfaceContainerHighDark,
    surfaceContainerHighest = SukiSurfaceContainerHighestDark,
)

@Composable
fun SukiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> SukiDarkColorScheme
        else -> SukiLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SukiTypography,
        content = content,
    )
}
