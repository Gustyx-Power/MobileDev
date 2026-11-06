package com.gdgocwidyatama.sukiwalls.ui.screens.contributors.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgocwidyatama.sukiwalls.data.model.Contributor
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded
import com.gdgocwidyatama.sukiwalls.ui.components.NetworkImage
import com.gdgocwidyatama.sukiwalls.ui.components.SukiListItem

@Composable
fun ContributorItem(
    contributor: Contributor,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    SukiListItem(
        title = contributor.name,
        subtitle = contributor.role,
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        onClick = {
            if (contributor.github.isNotBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(contributor.github))
                context.startActivity(intent)
            }
        },
        modifier = modifier,
        leadingContent = {
            if (!contributor.avatarUrl.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape),
                ) {
                    NetworkImage(
                        url = contributor.avatarUrl,
                        contentDescription = contributor.name,
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = contributor.avatarLetter,
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 16.sp,
                    )
                }
            }
        },
    )
}
