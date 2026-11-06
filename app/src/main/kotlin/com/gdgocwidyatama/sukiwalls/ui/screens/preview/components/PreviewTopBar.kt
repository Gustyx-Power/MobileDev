package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gdgocwidyatama.sukiwalls.R
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Text

@Composable
fun PreviewTopBar(
    onExitClick: () -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = onExitClick,
            colors = ButtonDefaults.buttonColors(
                color = Color(0xFF2C2C30),
            ),
        ) {
            Text(
                text = stringResource(R.string.action_exit),
                color = Color.White,
            )
        }

        Button(
            onClick = onApplyClick,
            colors = ButtonDefaults.buttonColorsPrimary(
                color = Color(0xFF2979FF),
            ),
        ) {
            Text(
                text = stringResource(R.string.action_apply_hyper),
                color = Color.White,
            )
        }
    }
}
