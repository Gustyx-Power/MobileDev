package com.gdgocwidyatama.sukiwalls.ui.screens.preview

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenConfig
import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper
import com.gdgocwidyatama.sukiwalls.data.repository.WallpaperRepository
import com.gdgocwidyatama.sukiwalls.ui.components.ImageCacheManager
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.ApplyWallpaperBottomSheet
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.CarouselHintPill
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.LockscreenCarouselPager
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.LockscreenCustomizerDock
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.PreviewBottomBar
import com.gdgocwidyatama.sukiwalls.ui.screens.preview.components.PreviewTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    val initialIndex = remember(allWallpapers, wallpaper) {
        val found = allWallpapers.indexOfFirst { it.id == wallpaper.id }
        if (found >= 0) found else 0
    }

    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        pageCount = { allWallpapers.size },
    )

    var isEditMode by remember { mutableStateOf(false) }
    var isCarouselActive by remember { mutableStateOf(false) }
    var lockConfig by remember { mutableStateOf(LockscreenConfig()) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var isApplying by remember { mutableStateOf(false) }
    var selectedTargetFlag by remember {
        mutableIntStateOf(WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val currentWallpaper = allWallpapers.getOrNull(pagerState.currentPage) ?: wallpaper

    BackHandler(enabled = true) {
        when {
            isCarouselActive -> isCarouselActive = false
            isEditMode -> isEditMode = false
            else -> onBackClick()
        }
    }

    val canvasScale by animateFloatAsState(
        targetValue = when {
            isCarouselActive -> 0.78f
            isEditMode -> 0.84f
            isApplying -> 0.92f
            else -> 1.0f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "canvasScale",
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PreviewTopBar(
            currentWallpaper = currentWallpaper,
            isEditMode = isEditMode || isCarouselActive,
            onBackClick = {
                when {
                    isCarouselActive -> isCarouselActive = false
                    isEditMode -> isEditMode = false
                    else -> onBackClick()
                }
            },
            onToggleEditMode = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                isEditMode = !isEditMode
            },
        )

        if (isCarouselActive) {
            CarouselHintPill(
                isCarouselActive = true,
                isEditMode = isEditMode,
                onToggle = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    isCarouselActive = false
                },
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 4.dp)
                .graphicsLayer {
                    scaleX = canvasScale
                    scaleY = canvasScale
                },
            contentAlignment = Alignment.Center,
        ) {
            LockscreenCarouselPager(
                wallpapers = allWallpapers,
                pagerState = pagerState,
                config = lockConfig,
                isEditMode = isEditMode,
                isCarouselActive = isCarouselActive,
                onScrollUp = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    isCarouselActive = true
                },
                onScrollDown = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    isCarouselActive = false
                },
                onLongPress = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    isEditMode = true
                },
                onTap = {
                    if (isCarouselActive) {
                        isCarouselActive = false
                    } else if (isEditMode) {
                        isEditMode = false
                    }
                },
            )

            if (isApplying) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.9f), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp),
                        strokeWidth = 3.dp,
                    )
                }
            }
        }

        if (!isCarouselActive && !isEditMode) {
            CarouselHintPill(
                isCarouselActive = false,
                isEditMode = false,
                onToggle = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    isCarouselActive = true
                },
            )
        }

        if (!isEditMode) {
            PreviewBottomBar(onApplyClick = { showBottomSheet = true })
        } else {
            AnimatedVisibility(
                visible = isEditMode,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
            ) {
                LockscreenCustomizerDock(
                    config = lockConfig,
                    isCarouselActive = isCarouselActive,
                    onToggleCarousel = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        isCarouselActive = !isCarouselActive
                    },
                    onConfigChange = { lockConfig = it },
                    onDoneClick = {
                        isEditMode = false
                        isCarouselActive = false
                    },
                    onApplyClick = { showBottomSheet = true },
                )
            }
        }
    }

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
                                targetUrl.isNotBlank() -> ImageCacheManager.loadImage(appContext, targetUrl)
                                currentWallpaper.drawableResId != 0 -> BitmapFactory.decodeResource(appContext.resources, currentWallpaper.drawableResId)
                                else -> null
                            }
                            if (bitmap != null) {
                                val wm = WallpaperManager.getInstance(appContext)
                                wm.setBitmap(bitmap, null, true, flag)
                            }
                        }
                    } catch (_: Exception) {
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
