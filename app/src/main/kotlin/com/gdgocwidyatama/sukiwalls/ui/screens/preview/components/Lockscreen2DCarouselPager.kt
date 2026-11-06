package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenClockStyle
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenConfig
import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun Lockscreen2DCarouselPager(
    categories: List<LockscreenClockStyle>,
    wallpapersByCategory: Map<LockscreenClockStyle, List<Wallpaper>>,
    verticalPagerState: PagerState,
    horizontalPagerStates: Map<LockscreenClockStyle, PagerState>,
    config: LockscreenConfig,
    onCustomizeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val cardHeight = maxHeight * 0.78f
        val cardWidth = cardHeight * (9f / 19.5f)
        val verticalPadding = ((maxHeight - cardHeight) / 2).coerceAtLeast(24.dp)
        val horizontalPadding = ((maxWidth - cardWidth) / 2).coerceAtLeast(36.dp)

        // 1. Vertical Pager: Swiping up/down switches category layers (e.g. Klasik -> Rombo -> Majalah)
        VerticalPager(
            state = verticalPagerState,
            pageSize = PageSize.Fixed(cardHeight),
            contentPadding = PaddingValues(vertical = verticalPadding),
            pageSpacing = 16.dp,
            beyondViewportPageCount = 1,
            modifier = Modifier.fillMaxSize(),
        ) { vPage ->
            val category = categories[vPage]
            val wallpapers = wallpapersByCategory[category] ?: emptyList()
            val hPagerState = horizontalPagerStates[category]

            val vOffset = ((verticalPagerState.currentPage - vPage) + verticalPagerState.currentPageOffsetFraction).absoluteValue
            val isCurrentCategory = vPage == verticalPagerState.currentPage

            val vScale = lerp(0.88f, 1.0f, 1f - vOffset.coerceIn(0f, 1f))
            val vAlpha = lerp(0.55f, 1.0f, 1f - vOffset.coerceIn(0f, 1f))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = vScale
                        scaleY = vScale
                        alpha = vAlpha
                    },
                contentAlignment = Alignment.Center,
            ) {
                if (hPagerState != null && wallpapers.isNotEmpty()) {
                    // 2. Horizontal Pager: Swiping left/right browses wallpapers within this category
                    HorizontalPager(
                        state = hPagerState,
                        pageSize = PageSize.Fixed(cardWidth),
                        contentPadding = PaddingValues(horizontal = horizontalPadding),
                        pageSpacing = 14.dp,
                        beyondViewportPageCount = 1,
                        modifier = Modifier.fillMaxSize(),
                    ) { hPage ->
                        val wallpaper = wallpapers[hPage]
                        val hOffset = ((hPagerState.currentPage - hPage) + hPagerState.currentPageOffsetFraction).absoluteValue
                        val isCenterCard = isCurrentCategory && hPage == hPagerState.currentPage

                        val hScale = lerp(0.85f, 1.0f, 1f - hOffset.coerceIn(0f, 1f))
                        val hAlpha = lerp(0.50f, 1.0f, 1f - hOffset.coerceIn(0f, 1f))

                        Box(
                            modifier = Modifier
                                .height(cardHeight)
                                .width(cardWidth)
                                .graphicsLayer {
                                    scaleX = hScale
                                    scaleY = hScale
                                    alpha = hAlpha
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            LockscreenCanvasCard(
                                wallpaper = wallpaper,
                                config = config.copy(clockStyle = category),
                                isActiveCard = isCenterCard,
                                onCustomizeClick = onCustomizeClick,
                                onCardClick = {
                                    scope.launch {
                                        if (!isCurrentCategory) {
                                            verticalPagerState.animateScrollToPage(vPage)
                                        }
                                        hPagerState.animateScrollToPage(hPage)
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

