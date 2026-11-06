package com.gdgocwidyatama.sukiwalls.ui.screens.preview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BatteryFull
import androidx.compose.material.icons.rounded.NetworkCell
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdgocwidyatama.sukiwalls.theme.GoogleSansRounded

@Composable
fun LockscreenStatusHeader(
    tint: Color = Color.White.copy(alpha = 0.90f),
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "GDGoC",
            style = MaterialTheme.typography.labelSmall,
            fontFamily = GoogleSansRounded,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            color = tint,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Rounded.NetworkCell,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(13.dp),
            )
            Icon(
                imageVector = Icons.Rounded.Wifi,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(13.dp),
            )
            Text(
                text = "88%",
                style = MaterialTheme.typography.labelSmall,
                fontFamily = GoogleSansRounded,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                color = tint,
            )
            Icon(
                imageVector = Icons.Rounded.BatteryFull,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(14.dp),
            )
        }
    }
}
