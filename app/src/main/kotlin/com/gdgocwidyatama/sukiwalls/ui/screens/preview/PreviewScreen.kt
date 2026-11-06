package com.gdgocwidyatama.sukiwalls.ui.screens.preview

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gdgocwidyatama.sukiwalls.R
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenClockStyle
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenConfig
import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper
import com.gdgocwidyatama.sukiwalls.data.repository.WallpaperRepository
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded
import com.gdgocwidyatama.sukiwalls.ui.components.ImageCacheManager
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.ApplyWallpaperBottomSheet
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.Lockscreen2DCarouselPager
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.LockscreenCustomizerDock
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.PreviewTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    wallpaper: Wallpaper,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    val allWallpapers by WallpaperRepository.wallpapers.collectAsStateWithLifecycle()

    val categories = remember {
        listOf(
            LockscreenClockStyle.KLASIK,
            LockscreenClockStyle.ROMBO,
            LockscreenClockStyle.MAJALAH,
            LockscreenClockStyle.GEOMETRI,
            LockscreenClockStyle.MINIMALIS,
        )
    }

    val wallpapersByCategory = remember(allWallpapers) {
        categories.associateWith { cat ->
            val matches = allWallpapers.filter { it.category.equals(cat.displayName, ignoreCase = true) }
            if (matches.isNotEmpty()) matches else allWallpapers.take(5)
        }
    }

    val initialCategory = remember(wallpaper) {
        categories.firstOrNull { it.displayName.equals(wallpaper.category, ignoreCase = true) }
            ?: LockscreenClockStyle.KLASIK
    }
    val initialCategoryIndex = remember(initialCategory) {
        categories.indexOf(initialCategory).coerceAtLeast(0)
    }
    val initialWallpaperIndex = remember(wallpaper, wallpapersByCategory, initialCategory) {
        val list = wallpapersByCategory[initialCategory] ?: emptyList()
        val idx = list.indexOfFirst { it.id == wallpaper.id }
        if (idx >= 0) idx else 0
    }

    // Vertical Pager: Category layers (Klasik -> Rombo -> Majalah -> Geometri -> Minimalis)
    val verticalPagerState = rememberPagerState(
        initialPage = initialCategoryIndex,
        pageCount = { categories.size },
    )

    // Horizontal Pagers: Each category layer has its own horizontal wallpaper carousel
    val hPagerKlasik = rememberPagerState(
        initialPage = if (initialCategory == LockscreenClockStyle.KLASIK) initialWallpaperIndex else 0,
        pageCount = { wallpapersByCategory[LockscreenClockStyle.KLASIK]?.size ?: 0 },
    )
    val hPagerRombo = rememberPagerState(
        initialPage = if (initialCategory == LockscreenClockStyle.ROMBO) initialWallpaperIndex else 0,
        pageCount = { wallpapersByCategory[LockscreenClockStyle.ROMBO]?.size ?: 0 },
    )
    val hPagerMajalah = rememberPagerState(
        initialPage = if (initialCategory == LockscreenClockStyle.MAJALAH) initialWallpaperIndex else 0,
        pageCount = { wallpapersByCategory[LockscreenClockStyle.MAJALAH]?.size ?: 0 },
    )
    val hPagerGeometri = rememberPagerState(
        initialPage = if (initialCategory == LockscreenClockStyle.GEOMETRI) initialWallpaperIndex else 0,
        pageCount = { wallpapersByCategory[LockscreenClockStyle.GEOMETRI]?.size ?: 0 },
    )
    val hPagerMinimalis = rememberPagerState(
        initialPage = if (initialCategory == LockscreenClockStyle.MINIMALIS) initialWallpaperIndex else 0,
        pageCount = { wallpapersByCategory[LockscreenClockStyle.MINIMALIS]?.size ?: 0 },
    )

    val horizontalPagerStates = remember(hPagerKlasik, hPagerRombo, hPagerMajalah, hPagerGeometri, hPagerMinimalis) {
        mapOf(
            LockscreenClockStyle.KLASIK to hPagerKlasik,
            LockscreenClockStyle.ROMBO to hPagerRombo,
            LockscreenClockStyle.MAJALAH to hPagerMajalah,
            LockscreenClockStyle.GEOMETRI to hPagerGeometri,
            LockscreenClockStyle.MINIMALIS to hPagerMinimalis,
        )
    }

    var isCustomizerOpen by remember { mutableStateOf(false) }
    var lockConfig by remember { mutableStateOf(LockscreenConfig()) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var isApplying by remember { mutableStateOf(false) }
    var selectedTargetFlag by remember {
        mutableIntStateOf(WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val currentCategory = categories.getOrElse(verticalPagerState.currentPage) { LockscreenClockStyle.KLASIK }
    val currentCategoryWallpapers = wallpapersByCategory[currentCategory] ?: emptyList()
    val currentHPager = horizontalPagerStates[currentCategory]
    val currentWallpaper = currentCategoryWallpapers.getOrNull(currentHPager?.currentPage ?: 0) ?: wallpaper

    BackHandler(enabled = true) {
        if (isCustomizerOpen) {
            isCustomizerOpen = false
        } else {
            onBackClick()
        }
    }

    MiuixTheme {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF121214))
                .statusBarsPadding()
                .navigationBarsPadding(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Top Bar with Miuix "Keluar" and "Terapkan"
                PreviewTopBar(
                    onExitClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        if (isCustomizerOpen) {
                            isCustomizerOpen = false
                        } else {
                            onBackClick()
                        }
                    },
                    onApplyClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showBottomSheet = true
                    },
                )

                // Category Title (e.g. "Klasik", "Rombo", "Majalah", "Geometri", "Minimalis")
                Text(
                    text = currentCategory.displayName,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 2D HyperOS Showcase:
                // - Swipe Left/Right: Browse wallpapers in current category (side cards peek)
                // - Scroll Up/Down: Switch category layer (bottom/top cards peek)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Lockscreen2DCarouselPager(
                        categories = categories,
                        wallpapersByCategory = wallpapersByCategory,
                        verticalPagerState = verticalPagerState,
                        horizontalPagerStates = horizontalPagerStates,
                        config = lockConfig,
                        onCustomizeClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            isCustomizerOpen = true
                        },
                    )

                    if (isApplying) {
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .background(Color(0xCC1A1A1E), CircleShape),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFF2979FF),
                                modifier = Modifier.size(36.dp),
                                strokeWidth = 3.dp,
                            )
                        }
                    }
                }
            }

            // Customizer Dock
            AnimatedVisibility(
                visible = isCustomizerOpen,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                LockscreenCustomizerDock(
                    config = lockConfig.copy(clockStyle = currentCategory),
                    onConfigChange = { updated ->
                        lockConfig = updated
                        if (updated.clockStyle != currentCategory) {
                            val newIdx = categories.indexOf(updated.clockStyle)
                            if (newIdx >= 0) {
                                scope.launch {
                                    verticalPagerState.animateScrollToPage(newIdx)
                                }
                            }
                        }
                    },
                    onDoneClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        isCustomizerOpen = false
                    },
                )
            }
        }
    }

    // Apply Wallpaper Bottom Sheet
    if (showBottomSheet) {
        ApplyWallpaperBottomSheet(
            selectedTargetFlag = selectedTargetFlag,
            onSelectTarget = { flag ->
                selectedTargetFlag = flag
                showBottomSheet = false
                isApplying = true
                val appContext = context.applicationContext
                scope.launch {
                    try {
                        withContext(NonCancellable + Dispatchers.IO) {
                            val targetUrl = currentWallpaper.downloadUrl.ifBlank { currentWallpaper.imageUrl }
                            val bitmap = when {
                                currentWallpaper.drawableResId != 0 -> {
                                    BitmapFactory.decodeResource(appContext.resources, currentWallpaper.drawableResId)
                                }
                                targetUrl.isNotBlank() -> {
                                    ImageCacheManager.loadImage(appContext, targetUrl)
                                }
                                else -> null
                            }
                            if (bitmap != null) {
                                val wm = WallpaperManager.getInstance(appContext)
                                wm.setBitmap(bitmap, null, true, flag)
                            }
                        }
                        Toast.makeText(
                            appContext,
                            appContext.getString(R.string.wallpaper_applied_success),
                            Toast.LENGTH_SHORT,
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(appContext, "Gagal menerapkan: ${e.message}", Toast.LENGTH_SHORT).show()
                    } finally {
                        isApplying = false
                    }
                }
            },
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        )
    }
}
