package com.gdgocwidyatama.sukiwalls.ui.navigation

import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper

sealed interface AppScreen {
    data class MainTab(val tab: SukiTab) : AppScreen
    data class Preview(val wallpaper: Wallpaper) : AppScreen
    data object Settings : AppScreen
}
