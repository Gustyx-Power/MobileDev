package com.gdgoc.luminawalls.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gdgoc.luminawalls.theme.GoogleSansRounded

/**
 * Global Screen Scaffolding:
 * Standardized background + Top Brand Header + Stacked Sheet Foreground Surface.
 */
@Composable
fun LuminaScreenScaffold(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    leadingAction: @Composable (() -> Unit)? = null,
    trailingAction: @Composable (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            leadingAction?.invoke()

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = GoogleSansRounded,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            trailingAction?.invoke()
        }

        // Foreground Stacked Rounded Sheet
        Surface(
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                content = content,
            )
        }
    }
}

/**
 * Reusable Section Title & Subtitle.
 */
@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = GoogleSansRounded,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            fontFamily = GoogleSansRounded,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/**
 * Reusable Connected Card Corner Shape Helper.
 */
fun itemShapeFor(
    index: Int,
    total: Int,
    outerCorner: Dp = 24.dp,
    innerCorner: Dp = 6.dp,
): RoundedCornerShape =
    when {
        total <= 1 -> RoundedCornerShape(outerCorner)
        index == 0 ->
            RoundedCornerShape(
                topStart = outerCorner,
                topEnd = outerCorner,
                bottomStart = innerCorner,
                bottomEnd = innerCorner,
            )
        index == total - 1 ->
            RoundedCornerShape(
                topStart = innerCorner,
                topEnd = innerCorner,
                bottomStart = outerCorner,
                bottomEnd = outerCorner,
            )
        else -> RoundedCornerShape(innerCorner)
    }

/**
 * 4. Reusable List Item Row (Avatar/Icon + Title + Subtitle + Trailing Badge/Action)
 */
@Composable
fun LuminaListItem(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(16.dp),
    containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingBadgeText: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    Surface(
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        shape = shape,
        color = containerColor,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            leadingContent?.invoke()

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                )
                if (!subtitle.isNullOrBlank()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = GoogleSansRounded,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    )
                }
            }

            if (trailingContent != null) {
                trailingContent()
            } else if (!trailingBadgeText.isNullOrBlank()) {
                Surface(
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Text(
                        text = trailingBadgeText,
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    )
                }
            }
        }
    }
}

