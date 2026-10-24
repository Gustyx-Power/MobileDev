package com.gdgoc.luminawalls.data.repository

import com.gdgoc.luminawalls.data.model.Wallpaper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Repository data wallpaper (lokal dan remote).
 */
object WallpaperRepository {

    private const val BASE_URL = "https://lumiwalls.pavelc4.workers.dev"
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // TODO: [Session 2] Inisialisasi daftar wallpaper dummy awal untuk kanvas Home Screen di bawah sini:



    private val initialWallpapers = emptyList<Wallpaper>()

    private val _wallpapers = MutableStateFlow<List<Wallpaper>>(initialWallpapers)
    val wallpapers: StateFlow<List<Wallpaper>> = _wallpapers.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {

        // TODO: [Session 5] Panggil loadWallpapers() saat menghubungkan API Cloudflare Worker di bawah sini:



    }

    suspend fun fetchWallpapersFromApi(category: String? = null): List<Wallpaper> = withContext(Dispatchers.IO) {

        // TODO: [Session 5] Eksekusi HTTP request ke BASE_URL via OkHttp dan deserialisasi JSON array ke List<Wallpaper> di bawah sini:



        emptyList()
    }

    fun loadWallpapers(category: String = "all", randomize: Boolean = false) {
        scope.launch {

            // TODO: [Session 5] Panggil fetchWallpapersFromApi() dan perbarui _wallpapers.value di bawah sini:



        }
    }

    fun getWallpaperById(id: String): Wallpaper? {
        return _wallpapers.value.firstOrNull { it.id == id }
    }
}
