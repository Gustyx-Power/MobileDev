package com.gdgoc.luminawalls.ui.screens.preview

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgoc.luminawalls.data.model.Wallpaper
import com.gdgoc.luminawalls.theme.GoogleSansRounded
import com.gdgoc.luminawalls.ui.components.ImageCacheManager
import com.gdgoc.luminawalls.ui.components.NetworkImage
import com.gdgoc.luminawalls.ui.components.bouncyClickable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Preview Screen inspired by ShinkaiWalls / Auriya:
 * - Floating rounded phone canvas in the center
 * - Top bar with Back button, Title & Subtitle, and Favorite toggle
 * - Bottom action bar with "Apply" button
 * - Clean ModalBottomSheet for Home/Lock/Both target selection
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    wallpaper: Wallpaper,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isApplying by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedTargetFlag by remember {
        mutableIntStateOf(WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM)
    }

    BackHandler(enabled = !showBottomSheet, onBack = onBackClick)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // --- 1. Top Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // TODO: [Session 4] Tulis FilledIconButton kembali (onBackClick) di bawah sini:



            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = wallpaper.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = androidx.compose.ui.res.stringResource(
                        com.gdgoc.luminawalls.R.string.preview_crafted_by,
                        wallpaper.author,
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = GoogleSansRounded,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        // --- 2. Floating Phone Canvas (Center) ---
        val scaleAnimate by animateFloatAsState(
            targetValue = if (isApplying) 0.92f else 1.0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow,
            ),
            label = "PreviewScale",
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 32.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(9f / 19.5f)
                    .graphicsLayer {
                        scaleX = scaleAnimate
                        scaleY = scaleAnimate
                    }
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(32.dp),
                        spotColor = Color.Black.copy(alpha = 0.5f),
                    )
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                        BorderStroke(
                            1.5.dp,
                            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f),
                        ),
                        RoundedCornerShape(32.dp),
                    )
                    .background(Color.Black),
            ) {
                NetworkImage(
                    url = wallpaper.imageUrl,
                    contentDescription = wallpaper.title,
                    modifier = Modifier.fillMaxSize(),
                )

                // Loading spinner overlay while applying
                if (isApplying) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.88f),
                                shape = CircleShape,
                            ),
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
        }

        // --- 3. Bottom Apply Bar ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 20.dp),
            contentAlignment = Alignment.Center,
        ) {
            // TODO: [Session 5] Tulis komponen Button untuk memicu BottomSheet apply wallpaper di bawah sini:



        }
    }

    // --- 4. Apply Bottom Sheet ---
    if (showBottomSheet) {
        ApplyWallpaperBottomSheet(
            selectedTargetFlag = selectedTargetFlag,
            onSelectTarget = { flag ->
                selectedTargetFlag = flag
                showBottomSheet = false
                isApplying = true
                val appContext = context.applicationContext
                coroutineScope.launch {
                    try {
                        withContext(NonCancellable + Dispatchers.IO) {
                            val targetUrl = wallpaper.downloadUrl.ifBlank { wallpaper.imageUrl }
                            val bitmap = when {
                                targetUrl.isNotBlank() -> {
                                    ImageCacheManager.loadImage(appContext, targetUrl)
                                        ?: ImageCacheManager.loadImage(appContext, wallpaper.imageUrl)
                                }
                                wallpaper.drawableResId != 0 -> {
                                    BitmapFactory.decodeResource(appContext.resources, wallpaper.drawableResId)
                                }
                                else -> null
                            }

                            checkNotNull(bitmap) { "Failed to load wallpaper image" }

                            // TODO: [Session 5] Tulis eksekusi WallpaperManager untuk mengubah wallpaper perangkat di bawah sini:



                        }
                    } catch (e: Exception) {
                        // Ignore or log error
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
