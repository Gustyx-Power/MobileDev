package com.gdgoc.luminawalls.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gdgoc.luminawalls.R
import com.gdgoc.luminawalls.data.model.Wallpaper
import com.gdgoc.luminawalls.ui.components.LuminaScreenScaffold

/**
 * Layar Beranda (Home): Menampilkan judul dan katalog wallpaper.
 */
@Composable
fun HomeScreen(
    wallpapers: List<Wallpaper>,
    onWallpaperClick: (Wallpaper) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LuminaScreenScaffold(
        title = stringResource(R.string.app_name),
        subtitle = stringResource(R.string.home_subtitle),
        modifier = modifier,
        trailingAction = {

            // TODO: [Session 3] Tulis IconButton untuk membuka layar Pengaturan (onSettingsClick) di bawah sini:



        },
    ) {

        // TODO: [Session 2] Tampilkan Stacking Card awal (WallpaperCard) di bawah sini:



        // TODO: [Session 3] Ubah menjadi LazyVerticalGrid 2 kolom di bawah sini:



    }
}
