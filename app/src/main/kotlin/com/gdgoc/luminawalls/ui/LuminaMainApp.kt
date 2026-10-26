package com.gdgoc.luminawalls.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gdgoc.luminawalls.data.model.Wallpaper
import com.gdgoc.luminawalls.data.repository.AppThemeMode
import com.gdgoc.luminawalls.data.repository.ContributorRepository
import com.gdgoc.luminawalls.data.repository.UserPreferencesRepository
import com.gdgoc.luminawalls.data.repository.WallpaperRepository
import com.gdgoc.luminawalls.theme.LuminaTheme
import com.gdgoc.luminawalls.ui.components.FloatingNavBar
import com.gdgoc.luminawalls.ui.components.LuminaTab
import com.gdgoc.luminawalls.ui.screens.contributors.ContributorsScreen
import com.gdgoc.luminawalls.ui.screens.home.HomeScreen
import com.gdgoc.luminawalls.ui.screens.preview.PreviewScreen
import com.gdgoc.luminawalls.ui.screens.settings.SettingsScreen
import com.gdgoc.luminawalls.ui.setup.SetupScreen

sealed interface AppScreen {
    data class MainTab(val tab: LuminaTab) : AppScreen
    data class Preview(val wallpaper: Wallpaper) : AppScreen
    data object Settings : AppScreen
}

/**
 * Root Orchestrator: Mengatur alur navigasi aplikasi dan tema sistem.
 */
@Composable
fun LuminaMainApp() {
    val context = LocalContext.current
    val prefsRepo = remember { UserPreferencesRepository.getInstance(context) }

    val isSetupCompleted by prefsRepo.isSetupCompleted.collectAsStateWithLifecycle()
    val themeMode by prefsRepo.themeMode.collectAsStateWithLifecycle()

    val wallpapers by WallpaperRepository.wallpapers.collectAsStateWithLifecycle()
    val contributors by ContributorRepository.contributors.collectAsStateWithLifecycle()

    val isDarkTheme = when (themeMode) {
        AppThemeMode.FOLLOW_SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
        AppThemeMode.LIGHT -> false
        AppThemeMode.DARK -> true
    }

    LuminaTheme(
        darkTheme = isDarkTheme,
        dynamicColor = true,
    ) {
        var activeScreen by remember { mutableStateOf<AppScreen>(AppScreen.MainTab(LuminaTab.HOME)) }
        var currentTab by remember { mutableStateOf(LuminaTab.HOME) }

        if (!isSetupCompleted) {
            SetupScreen(
                currentThemeMode = themeMode,
                onThemeModeSelected = { prefsRepo.setThemeMode(it) },
                onFinished = { prefsRepo.setSetupCompleted(true) },
            )
        } else {
            // Handle Back Button navigation
            BackHandler(enabled = activeScreen is AppScreen.Preview || activeScreen is AppScreen.Settings || currentTab != LuminaTab.HOME) {
                if (activeScreen is AppScreen.Preview || activeScreen is AppScreen.Settings) {
                    activeScreen = AppScreen.MainTab(currentTab)
                } else if (currentTab != LuminaTab.HOME) {
                    currentTab = LuminaTab.HOME
                    activeScreen = AppScreen.MainTab(LuminaTab.HOME)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainerLow),
            ) {
                AnimatedContent(
                    targetState = activeScreen,
                    transitionSpec = {
                        val isOpeningSubScreen = targetState is AppScreen.Preview || targetState is AppScreen.Settings
                        val isReturningFromSubScreen = initialState is AppScreen.Preview || initialState is AppScreen.Settings

                        if (isOpeningSubScreen && !isReturningFromSubScreen) {
                            // Forward Deep Navigation (Standard M3 Slide + Fade)
                            (
                                slideInHorizontally(
                                    initialOffsetX = { fullWidth -> fullWidth },
                                    animationSpec = tween(300, easing = EaseOutCubic),
                                ) + fadeIn(animationSpec = tween(220, easing = EaseOutCubic))
                            ).togetherWith(
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> -(fullWidth * 0.2f).toInt() },
                                    animationSpec = tween(300, easing = EaseOutCubic),
                                ) + fadeOut(animationSpec = tween(180, easing = EaseInCubic))
                            )
                        } else if (isReturningFromSubScreen && !isOpeningSubScreen) {
                            // Return from Deep Navigation (Standard M3 Pop Slide + Fade)
                            (
                                slideInHorizontally(
                                    initialOffsetX = { fullWidth -> -(fullWidth * 0.2f).toInt() },
                                    animationSpec = tween(300, easing = EaseOutCubic),
                                ) + fadeIn(animationSpec = tween(220, easing = EaseOutCubic))
                            ).togetherWith(
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> fullWidth },
                                    animationSpec = tween(300, easing = EaseOutCubic),
                                ) + fadeOut(animationSpec = tween(180, easing = EaseInCubic))
                            )
                        } else {
                            // Bottom Nav Tab Switch (Clean M3 Crossfade)
                            fadeIn(animationSpec = tween(200, easing = EaseOutCubic))
                                .togetherWith(fadeOut(animationSpec = tween(150, easing = EaseInCubic)))
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    label = "ScreenTransition",
                ) { screen ->
                    when (screen) {
                        is AppScreen.Preview -> {
                            PreviewScreen(
                                wallpaper = screen.wallpaper,
                                onBackClick = { activeScreen = AppScreen.MainTab(currentTab) },
                            )
                        }

                        is AppScreen.Settings -> {
                            SettingsScreen(
                                currentThemeMode = themeMode,
                                onThemeModeSelected = { prefsRepo.setThemeMode(it) },
                                onBackClick = { activeScreen = AppScreen.MainTab(currentTab) },
                            )
                        }

                        is AppScreen.MainTab -> {
                            when (screen.tab) {
                                LuminaTab.HOME -> {
                                    HomeScreen(
                                        wallpapers = wallpapers,
                                        onWallpaperClick = { wall ->

                                            // TODO: [Session 4] Navigasikan ke PreviewScreen (AppScreen.Preview(wall)) di bawah sini:



                                        },
                                        onSettingsClick = {

                                            // TODO: [Session 3] Navigasikan ke SettingsScreen (AppScreen.Settings) di bawah sini:



                                        },
                                    )
                                }

                                LuminaTab.CONTRIBUTORS -> {
                                    ContributorsScreen(
                                        contributors = contributors,
                                    )
                                }
                            }
                        }
                    }
                }

                // TODO: [Session 3] Tulis Floating Navigation Bar di bagian bawah untuk navigasi tab Home & Contributors di bawah sini:



            }
        }
    }
}
