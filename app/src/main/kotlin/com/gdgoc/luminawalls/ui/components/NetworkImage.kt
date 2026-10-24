package com.gdgoc.luminawalls.ui.components

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Image loader helper.
 * Di Sesi 5: Aktifkan pemuatan gambar Coil setelah menambahkan dependensi coil-compose di build.gradle.kts.
 */
object ImageCacheManager {
    suspend fun loadImage(context: Context, url: String): Bitmap? = withContext(Dispatchers.IO) {
        // TODO: [Session 5] Aktifkan pemuatan Bitmap menggunakan Coil di bawah sini:
        null
    }
}

/**
 * Container gambar untuk kartu wallpaper.
 */
@Composable
fun NetworkImage(
    url: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    // TODO: [Session 5] Pasang Coil SubcomposeAsyncImage setelah menambahkan dependensi coil-compose di bawah sini:



    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
    )
}
