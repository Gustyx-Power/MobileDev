package com.gdgocwidyatama.sukiwalls.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.gdgocwidyatama.sukiwalls.R

enum class LockscreenClockStyle(val titleRes: Int) {
    HYPEROS_STACKED(R.string.lockscreen_clock_stacked),
    ORIGIN_SPLIT(R.string.lockscreen_clock_split),
    NEO_SERIF(R.string.lockscreen_clock_serif),
}

enum class LockscreenClockColor(val displayName: String, val color: Color?) {
    DYNAMIC_MONET("Monet", null),
    PURE_WHITE("White", Color(0xFFFFFFFF)),
    WARM_TERRACOTTA("Terracotta", Color(0xFFFFB68E)),
    SLATE_DARK("Slate", Color(0xFF24252C)),
}

@Immutable
data class LockscreenConfig(
    val clockStyle: LockscreenClockStyle = LockscreenClockStyle.HYPEROS_STACKED,
    val clockColor: LockscreenClockColor = LockscreenClockColor.PURE_WHITE,
    val isDepthEnabled: Boolean = true,
)
