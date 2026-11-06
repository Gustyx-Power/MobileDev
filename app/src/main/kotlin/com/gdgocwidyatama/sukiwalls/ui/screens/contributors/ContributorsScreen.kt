package com.gdgocwidyatama.sukiwalls.ui.screens.contributors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gdgocwidyatama.sukiwalls.R
import com.gdgocwidyatama.sukiwalls.data.model.Contributor
import com.gdgocwidyatama.sukiwalls.data.repository.ContributorRepository
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded
import com.gdgocwidyatama.sukiwalls.ui.components.SukiScreenScaffold
import com.gdgocwidyatama.sukiwalls.ui.screens.contributors.components.ContributorItem
import com.gdgocwidyatama.sukiwalls.ui.screens.contributors.components.GdgocTeamBlock

@Composable
fun ContributorsScreen(
    contributors: List<Contributor>,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        ContributorRepository.refreshIfNeeded()
    }

    SukiScreenScaffold(
        title = stringResource(R.string.about_title),
        subtitle = stringResource(R.string.about_subtitle),
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                GdgocTeamBlock()
            }

            item {
                Text(
                    text = stringResource(R.string.about_contributors_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = GoogleSansRounded,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            items(
                items = contributors,
                key = { it.name + it.github },
            ) { contributor ->
                ContributorItem(contributor = contributor)
            }
        }
    }
}
