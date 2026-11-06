package com.gdgocwidyatama.sukiwalls.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.People
import androidx.compose.ui.graphics.vector.ImageVector
import com.gdgocwidyatama.sukiwalls.R

enum class SukiTab(
    @param:StringRes val titleRes: Int,
    val icon: ImageVector,
) {
    HOME(R.string.tab_home, Icons.Rounded.Home),
    CONTRIBUTORS(R.string.tab_about, Icons.Rounded.People),
}
