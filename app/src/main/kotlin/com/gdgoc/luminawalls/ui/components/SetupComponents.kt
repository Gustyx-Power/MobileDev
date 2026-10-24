package com.gdgoc.luminawalls.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.gdgoc.luminawalls.theme.GoogleSansRounded

/**
 * Clean & minimal Setup Bottom Bar for beginner study jam.
 * Uses standard Material 3 Button and simple step indicator.
 */
@Composable
fun SetupBottomBar(
    currentPage: Int,
    pageCount: Int,
    onNextClicked: () -> Unit,
    onFinishClicked: () -> Unit,
    isNextButtonEnabled: Boolean,
    isFinishButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val isLastPage = currentPage == pageCount - 1
    val isEnabled = if (isLastPage) isFinishButtonEnabled else isNextButtonEnabled

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedContent(
                targetState = currentPage,
                modifier = Modifier.weight(1f),
                transitionSpec = {
                    if (targetState > initialState) {
                        (slideInVertically { height -> height } + fadeIn()).togetherWith(
                            slideOutVertically { height -> -height } + fadeOut(),
                        )
                    } else {
                        (slideInVertically { height -> -height } + fadeIn()).togetherWith(
                            slideOutVertically { height -> height } + fadeOut(),
                        )
                    }
                },
                label = "StepTextAnimation",
            ) { targetPage ->
                if (targetPage == 0) {
                    Text(
                        text = androidx.compose.ui.res.stringResource(com.gdgoc.luminawalls.R.string.setup_step_begin),
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                } else {
                    Text(
                        text = androidx.compose.ui.res.stringResource(
                            com.gdgoc.luminawalls.R.string.setup_step_count,
                            targetPage,
                            pageCount - 1,
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = GoogleSansRounded,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            // TODO: [Session 2] Tulis Button navigasi wizard ("Lanjut" / "Mulai Jelajahi") di bawah sini:



        }
    }
}

@Composable
fun SetupHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall.copy(
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 1.15.em,
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = GoogleSansRounded,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
