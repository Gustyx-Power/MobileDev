package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenConfig
import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper
import kotlin.math.absoluteValue

@Composable
fun LockscreenCarouselPager(
    wallpapers: List<Wallpaper>,
    pagerState: PagerState,
    config: LockscreenConfig,
    isEditMode: Boolean,
    isCarouselActive: Boolean,
    onScrollUp: () -> Unit,
    onScrollDown: () -> Unit,
    onLongPress: () -> Unit,
    onTap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var accumulatedDragY by remember { mutableFloatStateOf(0f) }

    val dragModifier = Modifier.draggable(
        orientation = Orientation.Vertical,
        state = rememberDraggableState { delta ->
            accumulatedDragY += delta
            if (accumulatedDragY < -70f && !isCarouselActive) {
                onScrollUp()
                accumulatedDragY = 0f
            } else if (accumulatedDragY > 70f && isCarouselActive) {
                onScrollDown()
                accumulatedDragY = 0f
            }
        },
        onDragStopped = { velocity ->
            if (velocity < -350f && !isCarouselActive) {
                onScrollUp()
            } else if (velocity > 350f && isCarouselActive) {
                onScrollDown()
            }
            accumulatedDragY = 0f
        },
    )

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = isCarouselActive,
        contentPadding = PaddingValues(horizontal = if (isCarouselActive) 56.dp else if (isEditMode) 36.dp else 24.dp),
        pageSpacing = if (isCarouselActive) 20.dp else 16.dp,
        modifier = modifier
            .fillMaxSize()
            .then(dragModifier),
    ) { page ->
        val wallpaper = wallpapers[page]
        val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

        val scale = if (isCarouselActive) {
            lerp(0.84f, 1.0f, 1f - pageOffset.coerceIn(0f, 1f))
        } else {
            1.0f
        }
        val alpha = if (isCarouselActive) {
            lerp(0.45f, 1.0f, 1f - pageOffset.coerceIn(0f, 1f))
        } else {
            1.0f
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                },
            contentAlignment = Alignment.Center,
        ) {
            LockscreenCanvasCard(
                wallpaper = wallpaper,
                config = config,
                isEditMode = isEditMode || isCarouselActive,
                onLongPress = onLongPress,
                onTap = {
                    if (isCarouselActive) {
                        onScrollDown()
                    } else {
                        onTap()
                    }
                },
            )
        }
    }
}
