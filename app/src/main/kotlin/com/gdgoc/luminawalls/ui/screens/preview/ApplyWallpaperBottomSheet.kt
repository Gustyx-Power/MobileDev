package com.gdgoc.luminawalls.ui.screens.preview

import android.app.WallpaperManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gdgoc.luminawalls.theme.GoogleSansRounded
import com.gdgoc.luminawalls.ui.components.RichSelectionCard

/**
 * BottomSheet untuk memilih target pasang wallpaper (Home, Lock, atau Keduanya).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyWallpaperBottomSheet(
    selectedTargetFlag: Int,
    onSelectTarget: (flag: Int) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_sheet_title),
                style = MaterialTheme.typography.titleLarge,
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_sheet_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = GoogleSansRounded,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(18.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 36.dp),
            ) {
                // 1. Home & Lock Screen
                item {
                    val flag = WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
                    RichSelectionCard(
                        title = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_both_title),
                        subtitle = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_both_subtitle),
                        description = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_both_desc),
                        icon = Icons.Rounded.CheckCircle,
                        selected = selectedTargetFlag == flag,
                        onClick = { onSelectTarget(flag) },
                    )
                }

                // 2. Lock Screen
                item {
                    val flag = WallpaperManager.FLAG_LOCK
                    RichSelectionCard(
                        title = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_lock_title),
                        subtitle = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_lock_subtitle),
                        description = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_lock_desc),
                        icon = Icons.Rounded.Lock,
                        selected = selectedTargetFlag == flag,
                        onClick = { onSelectTarget(flag) },
                    )
                }

                // 3. Home Screen
                item {
                    val flag = WallpaperManager.FLAG_SYSTEM
                    RichSelectionCard(
                        title = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_home_title),
                        subtitle = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_home_subtitle),
                        description = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.apply_home_desc),
                        icon = Icons.Rounded.Home,
                        selected = selectedTargetFlag == flag,
                        onClick = { onSelectTarget(flag) },
                    )
                }
            }
        }
    }
}
