package com.gdgocwidyatama.sukiwalls.data.repository

import com.gdgocwidyatama.sukiwalls.data.model.Wallpaper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.concurrent.TimeUnit

object WallpaperRepository {

    private const val BASE_URL = "https://lumiwalls.pavelc4.workers.dev"
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private val initialWallpapers = listOf(
        Wallpaper(
            id = "wall-1",
            title = "Aesthetic Dunes",
            author = "Suki Studio",
            category = "Minimal",
            imageUrl = "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Minimal", "Warm", "Sand"),
        ),
        Wallpaper(
            id = "wall-2",
            title = "Tokyo Midnight Rain",
            author = "Kenji Sato",
            category = "Cyberpunk",
            imageUrl = "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("City", "Night", "Neon"),
        ),
        Wallpaper(
            id = "wall-3",
            title = "Nordic Mist Peaks",
            author = "Astrid Lind",
            category = "Nature",
            imageUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Mountain", "Fog", "Cold"),
        ),
        Wallpaper(
            id = "wall-4",
            title = "Brutalist Concrete Flow",
            author = "Elena Ramos",
            category = "Architecture",
            imageUrl = "https://images.unsplash.com/photo-1513694203232-719a280e022f?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Modern", "Geometry", "Shadow"),
        ),
        Wallpaper(
            id = "wall-5",
            title = "Cosmic Aurora Borealis",
            author = "Oliver Berg",
            category = "Space",
            imageUrl = "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Aurora", "Stars", "Vibrant"),
        ),
        Wallpaper(
            id = "wall-6",
            title = "Monochrome Waveforms",
            author = "Studio Mono",
            category = "Abstract",
            imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Dark", "Curves", "Glass"),
        ),
    )

    private val _wallpapers = MutableStateFlow<List<Wallpaper>>(initialWallpapers)
    val wallpapers: StateFlow<List<Wallpaper>> = _wallpapers.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadWallpapers()
    }

    suspend fun fetchWallpapersFromApi(category: String? = null): List<Wallpaper> = withContext(Dispatchers.IO) {
        val url = if (category != null && category != "all") "$BASE_URL/wallpapers?category=$category" else "$BASE_URL/wallpapers"
        val request = Request.Builder().url(url).build()

        try {
            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext emptyList()
                val body = response.body?.string() ?: return@withContext emptyList()
                val jsonArray = JSONArray(body)
                val list = mutableListOf<Wallpaper>()
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    list.add(
                        Wallpaper(
                            id = obj.optString("id", "api-$i"),
                            title = obj.optString("title", "Wallpaper $i"),
                            author = obj.optString("author", "GDGoC Community"),
                            category = obj.optString("category", "General"),
                            imageUrl = obj.optString("imageUrl", ""),
                            thumbUrl = obj.optString("thumbUrl", obj.optString("imageUrl", "")),
                            downloadUrl = obj.optString("downloadUrl", obj.optString("imageUrl", "")),
                        )
                    )
                }
                list
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun loadWallpapers(category: String = "all") {
        scope.launch {
            _isLoading.value = true
            val remote = fetchWallpapersFromApi(category)
            if (remote.isNotEmpty()) {
                _wallpapers.value = remote
            }
            _isLoading.value = false
        }
    }

    fun getWallpaperById(id: String): Wallpaper? {
        return _wallpapers.value.firstOrNull { it.id == id }
    }
}
