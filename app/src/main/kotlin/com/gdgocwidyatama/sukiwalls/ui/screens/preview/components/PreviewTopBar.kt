package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgocwidyatama.sukiwalls.R
import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded

@Composable
fun PreviewTopBar(
    currentWallpaper: Wallpaper,
    isEditMode: Boolean,
    onBackClick: () -> Unit,
    onToggleEditMode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FilledIconButton(
            onClick = onBackClick,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            modifier = Modifier.size(40.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = stringResource(R.string.action_back),
                modifier = Modifier.size(20.dp),
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (isEditMode) stringResource(R.string.lockscreen_customize_title) else currentWallpaper.title,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = if (isEditMode) stringResource(R.string.lockscreen_customize_hint) else stringResource(R.string.preview_crafted_by, currentWallpaper.author),
                style = MaterialTheme.typography.labelSmall,
                fontFamily = GoogleSansRounded,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        FilledIconButton(
            onClick = onToggleEditMode,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = if (isEditMode) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = if (isEditMode) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
            ),
            modifier = Modifier.size(40.dp),
        ) {
            Icon(
                imageVector = if (isEditMode) Icons.Rounded.Check else Icons.Rounded.Edit,
                contentDescription = null,
                modifier = Modifier.size(19.dp),
            )
        }
    }
}
