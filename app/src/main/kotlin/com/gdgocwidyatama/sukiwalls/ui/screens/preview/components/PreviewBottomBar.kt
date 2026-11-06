package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgocwidyatama.sukiwalls.R
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded

@Composable
fun PreviewBottomBar(
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = onApplyClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        ) {
            Text(
                text = stringResource(R.string.action_apply),
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(vertical = 4.dp),
            )
        }
    }
}
