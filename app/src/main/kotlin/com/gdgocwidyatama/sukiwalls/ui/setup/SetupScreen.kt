package com.gdgocwidyatama.sukiwalls.ui.setup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gdgocwidyatama.sukiwalls.R
import com.gdgocwidyatama.sukiwalls.data.repository.AppThemeMode
import com.gdgocwidyatama.sukiwalls.ui.screens.settings.components.ThemeOptionItem
import com.gdgocwidyatama.sukiwalls.ui.setup.components.SetupBottomBar
import com.gdgocwidyatama.sukiwalls.ui.setup.components.SetupHeader

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
            SetupBottomBar(
                currentPage = step,
                pageCount = pageCount,
                onNextClicked = { if (step < pageCount - 1) step += 1 },
                onFinishClicked = onFinished,
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    if (targetState > initialState) {
                        (slideInHorizontally { it } + fadeIn()).togetherWith(slideOutHorizontally { -it } + fadeOut())
                    } else {
                        (slideInHorizontally { -it } + fadeIn()).togetherWith(slideOutHorizontally { it } + fadeOut())
                    }
                },
                label = "wizardStep",
            ) { currentStep ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    when (currentStep) {
                        0 -> {
                            SetupHeader(
                                title = stringResource(R.string.setup_welcome_title),
                                subtitle = stringResource(R.string.setup_welcome_subtitle),
                            )
                        }
                        1 -> {
                            SetupHeader(
                                title = stringResource(R.string.setup_theme_title),
                                subtitle = stringResource(R.string.setup_theme_subtitle),
                            )
                            Column(
                                modifier = Modifier.padding(top = 28.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                ThemeOptionItem(
                                    title = stringResource(R.string.setup_theme_system),
                                    subtitle = stringResource(R.string.setup_theme_system_desc),
                                    icon = Icons.Rounded.PhoneAndroid,
                                    selected = currentThemeMode == AppThemeMode.FOLLOW_SYSTEM,
                                    onClick = { onThemeModeSelected(AppThemeMode.FOLLOW_SYSTEM) },
                                    shape = RoundedCornerShape(20.dp),
                                )
                                ThemeOptionItem(
                                    title = stringResource(R.string.setup_theme_dark),
                                    subtitle = stringResource(R.string.setup_theme_dark_desc),
                                    icon = Icons.Rounded.DarkMode,
                                    selected = currentThemeMode == AppThemeMode.DARK,
                                    onClick = { onThemeModeSelected(AppThemeMode.DARK) },
                                    shape = RoundedCornerShape(20.dp),
                                )
                                ThemeOptionItem(
                                    title = stringResource(R.string.setup_theme_light),
                                    subtitle = stringResource(R.string.setup_theme_light_desc),
                                    icon = Icons.Rounded.LightMode,
                                    selected = currentThemeMode == AppThemeMode.LIGHT,
                                    onClick = { onThemeModeSelected(AppThemeMode.LIGHT) },
                                    shape = RoundedCornerShape(20.dp),
                                )
                            }
                        }
                        2 -> {
                            SetupHeader(
                                title = stringResource(R.string.setup_done_title),
                                subtitle = stringResource(R.string.setup_done_subtitle),
                            )
                        }
                    }
                }
            }
        }
    }
}
