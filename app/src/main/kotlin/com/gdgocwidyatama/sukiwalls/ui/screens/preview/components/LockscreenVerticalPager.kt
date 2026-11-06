package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.gdgocwidyatama.sukiwalls.data.model.LockscreenConfig
import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun LockscreenVerticalPager(
    wallpapers: List<Wallpaper>,
    pagerState: PagerState,
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
        val verticalPadding = ((maxHeight - cardHeight) / 2).coerceAtLeast(32.dp)

        VerticalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(cardHeight),
            contentPadding = PaddingValues(vertical = verticalPadding),
            pageSpacing = 16.dp,
            beyondViewportPageCount = 1,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            val wallpaper = wallpapers[page]
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
            val isActive = page == pagerState.currentPage

            val scale = lerp(0.86f, 1.0f, 1f - pageOffset.coerceIn(0f, 1f))
            val alpha = lerp(0.55f, 1.0f, 1f - pageOffset.coerceIn(0f, 1f))

            Box(
                modifier = Modifier
                    .height(cardHeight)
                    .width(cardWidth)
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
                    isActiveCard = isActive,
                    onCustomizeClick = onCustomizeClick,
                    onCardClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    },
                )
            }
        }
    }
}

