package com.gdgoc.luminawalls.ui.setup

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.gdgoc.luminawalls.data.repository.AppThemeMode

/**
 * Setup & Onboarding Wizard (3-Step Pager: Welcome -> Personalization -> All Set).
 * Clean, minimal, and beginner-friendly using LuminaListItem.
 */
@Composable
fun SetupScreen(
    currentThemeMode: AppThemeMode,
    onThemeModeSelected: (AppThemeMode) -> Unit,
    onFinished: () -> Unit,
) {
    var step by remember { mutableIntStateOf(0) }
    val pageCount = 3

    BackHandler(enabled = step > 0) {
        step -= 1
    }

    Scaffold(
        bottomBar = {

            // TODO: [Session 2] Tulis SetupBottomBar untuk tombol navigasi wizard (Next / Finish) di bawah sini:



        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    val spec = tween<Float>(300)
                    if (targetState > initialState) {
                        (slideInHorizontally { width -> width } + fadeIn(spec))
                            .togetherWith(slideOutHorizontally { width -> -width } + fadeOut(spec))
                    } else {
                        (slideInHorizontally { width -> -width } + fadeIn(spec))
                            .togetherWith(slideOutHorizontally { width -> width } + fadeOut(spec))
                    }
                },
                modifier = Modifier.fillMaxSize(),
                label = "SetupStepTransition",
            ) { targetStep ->
                when (targetStep) {
                    0 -> WelcomeStep()
                    1 -> PersonalizationStep(
                        currentThemeMode = currentThemeMode,
                        onThemeModeSelected = onThemeModeSelected,
                    )
                    2 -> DoneStep()
                }
            }
        }
    }
}

// --- 1. Welcome Slide ---
@Composable
private fun WelcomeStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {

        // TODO: [Session 2] Tulis SetupHeader sambutan pembuka di bawah sini:



    }
}

// --- 2. Theme Selection Slide ---
private data class ThemeOption(
    val mode: AppThemeMode,
    @param:StringRes val titleRes: Int,
    @param:StringRes val descriptionRes: Int,
    val icon: ImageVector,
)

@Composable
private fun PersonalizationStep(
    currentThemeMode: AppThemeMode,
    onThemeModeSelected: (AppThemeMode) -> Unit,
) {
    val themeOptions = remember {
        listOf(
            ThemeOption(
                mode = AppThemeMode.FOLLOW_SYSTEM,
                titleRes = com.gdgoc.luminawalls.R.string.setup_theme_system,
                descriptionRes = com.gdgoc.luminawalls.R.string.setup_theme_system_desc,
                icon = Icons.Rounded.PhoneAndroid,
            ),
            ThemeOption(
                mode = AppThemeMode.DARK,
                titleRes = com.gdgoc.luminawalls.R.string.setup_theme_dark,
                descriptionRes = com.gdgoc.luminawalls.R.string.setup_theme_dark_desc,
                icon = Icons.Rounded.DarkMode,
            ),
            ThemeOption(
                mode = AppThemeMode.LIGHT,
                titleRes = com.gdgoc.luminawalls.R.string.setup_theme_light,
                descriptionRes = com.gdgoc.luminawalls.R.string.setup_theme_light_desc,
                icon = Icons.Rounded.LightMode,
            ),
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {

        // TODO: [Session 2] Tulis SetupHeader pemilihan tema di bawah sini:



        // TODO: [Session 2] Tulis opsi tema menggunakan Column & LuminaListItem di bawah sini:



    }
}

// --- 3. Completion Slide ---
@Composable
private fun DoneStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {

        // TODO: [Session 2] Tulis SetupHeader penutup (selesai) di bawah sini:



    }
}
