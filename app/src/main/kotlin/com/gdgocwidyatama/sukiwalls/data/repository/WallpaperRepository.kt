package com.gdgocwidyatama.sukiwalls.data.repository

import com.gdgocwidyatama.sukiwalls.R
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
        .connectTimeout(6, TimeUnit.SECONDS)
        .readTimeout(6, TimeUnit.SECONDS)
        .build()

    private val initialWallpapers = listOf(
        // 1. Official HyperOS Flagship 3D Renders (Bundled offline)
        Wallpaper(
            id = "hyperos-amber",
            title = "HyperOS Golden Ribbon",
            author = "Xiaomi HyperOS",
            category = "Klasik",
            drawableResId = R.drawable.hyperos_amber_gold,
            imageUrl = "",
            tags = listOf("HyperOS", "Klasik", "Amber", "Glass", "3D"),
        ),
        Wallpaper(
            id = "hyperos-blue",
            title = "HyperOS Cyan Flow",
            author = "Xiaomi HyperOS",
            category = "Klasik",
            drawableResId = R.drawable.hyperos_ocean_blue,
            imageUrl = "",
            tags = listOf("HyperOS", "Cyan", "Liquid", "Glass", "3D"),
        ),
        Wallpaper(
            id = "hyperos-rose",
            title = "HyperOS Rose Satin",
            author = "Xiaomi HyperOS",
            category = "Klasik",
            drawableResId = R.drawable.hyperos_rose_gold,
            imageUrl = "",
            tags = listOf("HyperOS", "Rose Gold", "Peach", "Silk", "3D"),
        ),
        Wallpaper(
            id = "hyperos-emerald",
            title = "HyperOS Emerald Prism",
            author = "Xiaomi HyperOS",
            category = "Klasik",
            drawableResId = R.drawable.hyperos_emerald_glass,
            imageUrl = "",
            tags = listOf("HyperOS", "Emerald", "Obsidian", "Glass", "3D"),
        ),
        Wallpaper(
            id = "hyperos-amethyst",
            title = "HyperOS Amethyst Silk",
            author = "Xiaomi HyperOS",
            category = "Klasik",
            drawableResId = R.drawable.hyperos_amethyst,
            imageUrl = "",
            tags = listOf("HyperOS", "Amethyst", "Magenta", "Crystal", "3D"),
        ),
        Wallpaper(
            id = "hyperos-titanium",
            title = "HyperOS Liquid Titanium",
            author = "Xiaomi HyperOS",
            category = "Klasik",
            drawableResId = R.drawable.hyperos_titanium,
            imageUrl = "",
            tags = listOf("HyperOS", "Titanium", "Mercury", "Silver", "3D"),
        ),
        Wallpaper(
            id = "hyperos-pastel",
            title = "HyperOS Sunset Dunes",
            author = "Xiaomi HyperOS",
            category = "Geometri",
            drawableResId = R.drawable.hyperos_pastel_dunes,
            imageUrl = "",
            tags = listOf("HyperOS", "Sunset", "Dunes", "Pastel", "Minimal"),
        ),
        Wallpaper(
            id = "hyperos-neon",
            title = "HyperOS Neon Amber Energy",
            author = "Xiaomi HyperOS",
            category = "Rombo",
            drawableResId = R.drawable.hyperos_neon_amber,
            imageUrl = "",
            tags = listOf("HyperOS", "Neon", "Energy", "Cyan", "Amber"),
        ),

        // 2. High-Definition Expressive Curated Catalog
        Wallpaper(
            id = "wall-1",
            title = "Aesthetic Warm Dunes",
            author = "Suki Studio",
            category = "Geometri",
            imageUrl = "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Minimal", "Warm", "Sand"),
        ),
        Wallpaper(
            id = "wall-2",
            title = "Tokyo Midnight Rain",
            author = "Kenji Sato",
            category = "Rombo",
            imageUrl = "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("City", "Night", "Neon"),
        ),
        Wallpaper(
            id = "wall-3",
            title = "Nordic Mist Peaks",
            author = "Astrid Lind",
            category = "Majalah",
            imageUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Mountain", "Fog", "Cold"),
        ),
        Wallpaper(
            id = "wall-4",
            title = "Brutalist Concrete Flow",
            author = "Elena Ramos",
            category = "Geometri",
            imageUrl = "https://images.unsplash.com/photo-1513694203232-719a280e022f?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Modern", "Geometry", "Shadow"),
        ),
        Wallpaper(
            id = "wall-5",
            title = "Cosmic Aurora Borealis",
            author = "Oliver Berg",
            category = "Minimalis",
            imageUrl = "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Aurora", "Stars", "Vibrant"),
        ),
        Wallpaper(
            id = "wall-6",
            title = "Monochrome Waveforms",
            author = "Studio Mono",
            category = "Klasik",
            imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Dark", "Curves", "Glass"),
        ),
        Wallpaper(
            id = "wall-7",
            title = "Cyberpunk Highway Lights",
            author = "Ryu Takahashi",
            category = "Rombo",
            imageUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Speed", "Light Trails", "Cyberpunk"),
        ),
        Wallpaper(
            id = "wall-8",
            title = "Alpine Reflection Mirror",
            author = "Lucas Meyer",
            category = "Majalah",
            imageUrl = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Lake", "Reflection", "Alps"),
        ),
        Wallpaper(
            id = "wall-9",
            title = "Prism Glass Geometry",
            author = "Studio Voxel",
            category = "Klasik",
            imageUrl = "https://images.unsplash.com/photo-1634017839464-5c339ebe3cb4?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Prism", "Glass", "Refraction"),
        ),
        Wallpaper(
            id = "wall-10",
            title = "Dolomites Golden Sunset",
            author = "Marco Rossi",
            category = "Majalah",
            imageUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Sunset", "Mountains", "Nature"),
        ),
        Wallpaper(
            id = "wall-11",
            title = "Deep Space Nebula",
            author = "NASA Vistas",
            category = "Minimalis",
            imageUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Cosmos", "Galaxy", "Nebula"),
        ),
        Wallpaper(
            id = "wall-12",
            title = "Bauhaus Spiral Architecture",
            author = "Klara Novak",
            category = "Geometri",
            imageUrl = "https://images.unsplash.com/photo-1550684848-fac1c5b4e853?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Spiral", "Monochrome", "Architecture"),
        ),
        Wallpaper(
            id = "wall-13",
            title = "Shinjuku Neon Alley",
            author = "Kenji Sato",
            category = "Rombo",
            imageUrl = "https://images.unsplash.com/photo-1514565131-fce0801e5785?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Tokyo", "Neon", "Rain"),
        ),
        Wallpaper(
            id = "wall-14",
            title = "Velvet Obsidian Flow",
            author = "Suki Studio",
            category = "Minimalis",
            imageUrl = "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Dark", "AMOLED", "Minimal"),
        ),
        Wallpaper(
            id = "wall-15",
            title = "Pastel Sand Waves",
            author = "Astrid Lind",
            category = "Geometri",
            imageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Beach", "Pastel", "Sand"),
        ),
        Wallpaper(
            id = "wall-16",
            title = "Emerald Pines in Mist",
            author = "Lucas Meyer",
            category = "Majalah",
            imageUrl = "https://images.unsplash.com/photo-1448375240586-882707db888b?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Forest", "Pines", "Mist"),
        ),
        Wallpaper(
            id = "wall-17",
            title = "Midnight Starlight Vista",
            author = "Oliver Berg",
            category = "Minimalis",
            imageUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Earth", "Night", "Stars"),
        ),
        Wallpaper(
            id = "wall-18",
            title = "Synthwave Violet Sunset",
            author = "Ryu Takahashi",
            category = "Rombo",
            imageUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Synthwave", "Violet", "Glow"),
        ),
        Wallpaper(
            id = "wall-19",
            title = "Abstract Liquid Spheres",
            author = "Studio Voxel",
            category = "Klasik",
            imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Abstract", "Liquid", "Sphere"),
        ),
        Wallpaper(
            id = "wall-20",
            title = "Minimalist Architectural Arch",
            author = "Elena Ramos",
            category = "Geometri",
            imageUrl = "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Arch", "Minimal", "Clean"),
        ),
        Wallpaper(
            id = "wall-21",
            title = "Cosmic Andromeda Horizon",
            author = "NASA Vistas",
            category = "Minimalis",
            imageUrl = "https://images.unsplash.com/photo-1462331940025-496dfbfc7564?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Andromeda", "Galaxy", "Stars"),
        ),
        Wallpaper(
            id = "wall-22",
            title = "Kyoto Rainy Night",
            author = "Kenji Sato",
            category = "Rombo",
            imageUrl = "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Kyoto", "Night", "Lantern"),
        ),
        Wallpaper(
            id = "wall-23",
            title = "Fjord Whispering Fog",
            author = "Astrid Lind",
            category = "Majalah",
            imageUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Fjord", "Mist", "Norway"),
        ),
        Wallpaper(
            id = "wall-24",
            title = "Iridescent Chromatic Silk",
            author = "Studio Mono",
            category = "Klasik",
            imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=1200&q=80",
            tags = listOf("Silk", "Iridescent", "Waves"),
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
                val combined = (initialWallpapers + remote).distinctBy { it.id }
                _wallpapers.value = combined
            }
            _isLoading.value = false
        }
    }

    fun getWallpaperById(id: String): Wallpaper? {
        return _wallpapers.value.firstOrNull { it.id == id }
    }
}
