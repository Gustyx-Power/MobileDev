package com.gdgocwidyatama.sukiwalls.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gdgocwidyatama.sukiwalls.data.repository.AppThemeMode
import com.gdgocwidyatama.sukiwalls.data.repository.ContributorRepository
import com.gdgocwidyatama.sukiwalls.data.repository.UserPreferencesRepository
import com.gdgocwidyatama.sukiwalls.data.repository.WallpaperRepository
import com.gdgocwidyatama.sukiwalls.theme.SukiTheme
import com.gdgocwidyatama.sukiwalls.ui.components.FloatingNavBar
import com.gdgocwidyatama.sukiwalls.ui.navigation.AppScreen
import com.gdgocwidyatama.sukiwalls.ui.navigation.SukiTab
import com.gdgocwidyatama.sukiwalls.ui.screens.contributors.ContributorsScreen
import com.gdgocwidyatama.sukiwalls.ui.screens.home.HomeScreen
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.PreviewScreen
import com.gdgocwidyatama.sukiwalls.ui.screens.settings.SettingsScreen
import com.gdgocwidyatama.sukiwalls.ui.setup.SetupScreen

@Composable
fun SukiMainApp() {
    val context = LocalContext.current
    val prefsRepo = remember { UserPreferencesRepository.getInstance(context) }

    val isSetupCompleted by prefsRepo.isSetupCompleted.collectAsStateWithLifecycle()
    val themeMode by prefsRepo.themeMode.collectAsStateWithLifecycle()

    val wallpapers by WallpaperRepository.wallpapers.collectAsStateWithLifecycle()
    val contributors by ContributorRepository.contributors.collectAsStateWithLifecycle()

    val isDarkTheme = when (themeMode) {
        AppThemeMode.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        AppThemeMode.LIGHT -> false
        AppThemeMode.DARK -> true
    }

    SukiTheme(
        darkTheme = isDarkTheme,
        dynamicColor = true,
    ) {
        var activeScreen by remember { mutableStateOf<AppScreen>(AppScreen.MainTab(SukiTab.HOME)) }
        var currentTab by remember { mutableStateOf(SukiTab.HOME) }

        if (!isSetupCompleted) {
            SetupScreen(
                currentThemeMode = themeMode,
                onThemeModeSelected = { prefsRepo.setThemeMode(it) },
                onFinished = { prefsRepo.setSetupCompleted(true) },
            )
        } else {
            BackHandler(enabled = activeScreen is AppScreen.Preview || activeScreen is AppScreen.Settings || currentTab != SukiTab.HOME) {
                if (activeScreen is AppScreen.Preview || activeScreen is AppScreen.Settings) {
                    activeScreen = AppScreen.MainTab(currentTab)
                } else if (currentTab != SukiTab.HOME) {
                    currentTab = SukiTab.HOME
                    activeScreen = AppScreen.MainTab(SukiTab.HOME)
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
                            (slideInHorizontally(animationSpec = tween(300, easing = EaseOutCubic)) { it } + fadeIn(tween(220)))
                                .togetherWith(slideOutHorizontally(animationSpec = tween(300, easing = EaseOutCubic)) { -(it * 0.2f).toInt() } + fadeOut(tween(180)))
                        } else if (isReturningFromSubScreen && !isOpeningSubScreen) {
                            (slideInHorizontally(animationSpec = tween(300, easing = EaseOutCubic)) { -(it * 0.2f).toInt() } + fadeIn(tween(220)))
                                .togetherWith(slideOutHorizontally(animationSpec = tween(300, easing = EaseOutCubic)) { it } + fadeOut(tween(180)))
                        } else {
                            fadeIn(tween(200, easing = EaseOutCubic)).togetherWith(fadeOut(tween(150, easing = EaseInCubic)))
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    label = "screenNav",
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
                                SukiTab.HOME -> {
                                    HomeScreen(
                                        wallpapers = wallpapers,
                                        onWallpaperClick = { activeScreen = AppScreen.Preview(it) },
                                        onSettingsClick = { activeScreen = AppScreen.Settings },
                                    )
                                }
                                SukiTab.CONTRIBUTORS -> {
                                    ContributorsScreen(contributors = contributors)
                                }
                            }
                        }
                    }
                }

                AnimatedVisibility(
                    visible = activeScreen is AppScreen.MainTab,
                    enter = slideInVertically { it } + fadeIn(),
                    exit = slideOutVertically { it } + fadeOut(),
                    modifier = Modifier.align(Alignment.BottomCenter),
                ) {
                    FloatingNavBar(
                        selectedTab = currentTab,
                        onTabSelected = { tab ->
                            currentTab = tab
                            activeScreen = AppScreen.MainTab(tab)
                        },
                    )
                }
            }
        }
    }
}
