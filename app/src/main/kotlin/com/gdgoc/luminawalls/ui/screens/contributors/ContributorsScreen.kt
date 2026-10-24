package com.gdgoc.luminawalls.ui.screens.contributors

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgoc.luminawalls.R
import com.gdgoc.luminawalls.data.model.Contributor
import com.gdgoc.luminawalls.data.repository.ContributorRepository
import com.gdgoc.luminawalls.theme.GoogleSansRounded
import com.gdgoc.luminawalls.ui.components.LuminaListItem
import com.gdgoc.luminawalls.ui.components.LuminaScreenScaffold
import com.gdgoc.luminawalls.ui.components.NetworkImage
import com.gdgoc.luminawalls.ui.components.SectionHeader
import com.gdgoc.luminawalls.ui.components.bouncyClickable
import com.gdgoc.luminawalls.ui.components.itemShapeFor

/**
 * Layar Kontributor & Tentang Aplikasi.
 */
@Composable
fun ContributorsScreen(
    contributors: List<Contributor>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    // Trigger repo fetch at most once across the entire app lifecycle
    LaunchedEffect(Unit) {
        ContributorRepository.refreshIfNeeded()
    }

    val openUrl: (String) -> Unit = { rawUrl ->
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(rawUrl)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
        }
    }

    LuminaScreenScaffold(
        title = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_title),
        subtitle = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_subtitle),
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            // TODO: [Session 3] Init Screen Kosongan: Tampilkan LuminaHeroCard di bawah sini:



            // TODO: [Session 4] Contributor Cards: Tampilkan GdgocTeamBlock dan ContributorsBlock di bawah sini:



        }
    }
}

/** Clean Auriya-style App Hero Card with icon, title, and description */
@Composable
private fun LuminaHeroCard(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                modifier = Modifier.size(54.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.gdg_logo),
                    contentDescription = "LuminaWalls",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = GoogleSansRounded,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_hero_desc),
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = GoogleSansRounded,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun GdgocTeamBlock(
    onInstructorClick: () -> Unit,
    onCoreTeamClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        // 1. Tech Division Lead Instructor (pavelc4)
        LuminaListItem(
            title = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_instructor_name),
            subtitle = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_instructor_subtitle),
            shape = itemShapeFor(0, 0),
            trailingBadgeText = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_badge_instructor),
            onClick = onInstructorClick,
            leadingContent = {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    modifier = Modifier.size(46.dp),
                ) {
                    NetworkImage(
                        url = "https://github.com/pavelc4.png",
                        contentDescription = "Instructor pavelc4",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                    )
                }
            },
        )

        // 2. GDGoC Core Team
        LuminaListItem(
            title = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_core_team_title),
            subtitle = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_core_team_subtitle),
            shape = itemShapeFor(0, 2),
            trailingBadgeText = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_badge_core_team),
            onClick = onCoreTeamClick,
            leadingContent = {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    modifier = Modifier.size(46.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.gdg_logo),
                        contentDescription = "GDGoC Widyatama",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }
            },
        )
        LuminaListItem(
            title = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_tech_team_title),
            subtitle = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_tech_team_subtitle),
            shape = itemShapeFor(1, 2),
            trailingBadgeText = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.about_badge_core_team),
            onClick = onCoreTeamClick,
            leadingContent = {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    modifier = Modifier.size(46.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.gdg_logo),
                        contentDescription = "GDGoC Tech Team",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }
            },
        )
    }
}

@Composable
private fun ContributorsBlock(
    contributors: List<Contributor>,
    onOpenGithub: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    // TODO: [Session 4] Tampilkan daftar kontributor menggunakan Column, LuminaListItem, dan avatar foto di bawah sini:



}

