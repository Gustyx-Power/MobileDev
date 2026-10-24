package com.gdgoc.luminawalls.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.gdgoc.luminawalls.R

// TODO: [Session 4] Material 3 Design — Typography tokens with Google Sans Rounded & Montserrat
private const val GoogleSansFlexRond = 100f

@OptIn(ExperimentalTextApi::class)
val GoogleSansRounded =
    FontFamily(
        Font(
            resId = R.font.gflex_variable,
            weight = FontWeight.Light,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(FontWeight.Light.weight),
                    FontVariation.Setting("ROND", GoogleSansFlexRond),
                ),
        ),
        Font(
            resId = R.font.gflex_variable,
            weight = FontWeight.Normal,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(FontWeight.Normal.weight),
                    FontVariation.Setting("ROND", GoogleSansFlexRond),
                ),
        ),
        Font(
            resId = R.font.gflex_variable,
            weight = FontWeight.Medium,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(FontWeight.Medium.weight),
                    FontVariation.Setting("ROND", GoogleSansFlexRond),
                ),
        ),
        Font(
            resId = R.font.gflex_variable,
            weight = FontWeight.SemiBold,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(FontWeight.SemiBold.weight),
                    FontVariation.Setting("ROND", GoogleSansFlexRond),
                ),
        ),
        Font(
            resId = R.font.gflex_variable,
            weight = FontWeight.Bold,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(FontWeight.Bold.weight),
                    FontVariation.Setting("ROND", GoogleSansFlexRond),
                ),
        ),
    )

val MontserratBold = FontFamily(Font(R.font.montserrat_bold, FontWeight.Bold))

val LuminaTypography =
    Typography(
        displayLarge =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Bold,
                fontSize = 57.sp,
                lineHeight = 64.sp,
                letterSpacing = (-0.25).sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                lineHeight = 52.sp,
            ),
        displaySmall =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                lineHeight = 44.sp,
            ),
        headlineLarge =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                lineHeight = 32.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
    )
