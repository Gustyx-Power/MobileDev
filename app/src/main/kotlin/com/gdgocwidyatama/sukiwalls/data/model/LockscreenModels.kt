package com.gdgocwidyatama.sukiwalls.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.gdgocwidyatama.sukiwalls.R

enum class LockscreenClockStyle(val displayName: String, val titleRes: Int) {
    KLASIK("Klasik", R.string.lockscreen_clock_serif),
    ROMBO("Rombo", R.string.lockscreen_clock_stacked),
    MAJALAH("Majalah", R.string.lockscreen_clock_split),
    GEOMETRI("Geometri", R.string.lockscreen_clock_minimal),
    MINIMALIS("Minimalis", R.string.lockscreen_clock_minimal),
}

enum class LockscreenClockColor(val displayName: String, val color: Color?) {
    PURE_WHITE("Putih", Color(0xFFFFFFFF)),
    DYNAMIC_MONET("Monet", null),
    WARM_TERRACOTTA("Terracotta", Color(0xFFFFB68E)),
    CYAN_NEON("Cyan", Color(0xFF38BDF8)),
    EMERALD_MINT("Mint", Color(0xFF34D399)),
    GOLDEN_SUN("Emas", Color(0xFFFBBF24)),
    SLATE_DARK("Slate", Color(0xFF24252C)),
}

@Immutable
data class LockscreenConfig(
    val clockStyle: LockscreenClockStyle = LockscreenClockStyle.KLASIK,
    val clockColor: LockscreenClockColor = LockscreenClockColor.PURE_WHITE,
    val isDepthEnabled: Boolean = true,
)
