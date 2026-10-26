package com.gdgoc.luminawalls.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// TODO: [Session 4] Material 3 Design — Theme, ColorSchemes & Dynamic Color
val LuminaLightColorScheme =
    lightColorScheme(
        primary = LuminaPrimaryLight,
        onPrimary = LuminaOnPrimaryLight,
        primaryContainer = LuminaPrimaryContainerLight,
        onPrimaryContainer = LuminaOnPrimaryContainerLight,
        secondary = LuminaSecondaryLight,
        onSecondary = LuminaOnSecondaryLight,
        secondaryContainer = LuminaSecondaryContainerLight,
        onSecondaryContainer = LuminaOnSecondaryContainerLight,
        tertiary = LuminaTertiaryLight,
        onTertiary = LuminaOnTertiaryLight,
        tertiaryContainer = LuminaTertiaryContainerLight,
        onTertiaryContainer = LuminaOnTertiaryContainerLight,
        background = LuminaBackgroundLight,
        onBackground = LuminaOnBackgroundLight,
        surface = LuminaSurfaceLight,
        onSurface = LuminaOnSurfaceLight,
        surfaceVariant = LuminaSurfaceVariantLight,
        onSurfaceVariant = LuminaOnSurfaceVariantLight,
        surfaceContainerLow = LuminaSurfaceContainerLowLight,
        surfaceContainer = LuminaSurfaceContainerLight,
        surfaceContainerHigh = LuminaSurfaceContainerHighLight,
        surfaceContainerHighest = LuminaSurfaceContainerHighestLight,
    )

val LuminaDarkColorScheme =
    darkColorScheme(
        primary = LuminaPrimaryDark,
        onPrimary = LuminaOnPrimaryDark,
        primaryContainer = LuminaPrimaryContainerDark,
        onPrimaryContainer = LuminaOnPrimaryContainerDark,
        secondary = LuminaSecondaryDark,
        onSecondary = LuminaOnSecondaryDark,
        secondaryContainer = LuminaSecondaryContainerDark,
        onSecondaryContainer = LuminaOnSecondaryContainerDark,
        tertiary = LuminaTertiaryDark,
        onTertiary = LuminaOnTertiaryDark,
        tertiaryContainer = LuminaTertiaryContainerDark,
        onTertiaryContainer = LuminaOnTertiaryContainerDark,
        background = LuminaBackgroundDark,
        onBackground = LuminaOnBackgroundDark,
        surface = LuminaSurfaceDark,
        onSurface = LuminaOnSurfaceDark,
        surfaceVariant = LuminaSurfaceVariantDark,
        onSurfaceVariant = LuminaOnSurfaceVariantDark,
        surfaceContainerLow = LuminaSurfaceContainerLowDark,
        surfaceContainer = LuminaSurfaceContainerDark,
        surfaceContainerHigh = LuminaSurfaceContainerHighDark,
        surfaceContainerHighest = LuminaSurfaceContainerHighestDark,
    )

@Composable
fun LuminaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> LuminaDarkColorScheme
            else -> LuminaLightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LuminaTypography,
        content = content,
    )
}
