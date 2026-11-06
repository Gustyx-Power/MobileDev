package com.gdgocwidyatama.sukiwalls.data.repository

import com.gdgocwidyatama.sukiwalls.data.model.Contributor
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

object ContributorRepository {

    const val DEFAULT_REPO_URL = "https://github.com/gdgocwidyatama/MobileDev"

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private val initialContributors = listOf(
        Contributor(
            name = "Dimas Dwi Ariyanto",
            role = "Tech Division Instructor",
            github = "https://github.com/dimasdwiar",
            bio = "Lead Android Instructor at GDGoC Widyatama University.",
            avatarLetter = "D",
        ),
        Contributor(
            name = "GDGoC Widyatama Core Team",
            role = "Organizer & Tech Lead",
            github = "https://github.com/gdgocwidyatama",
            bio = "Google Developer Groups on Campus - Widyatama University.",
            avatarLetter = "G",
        ),
    )

    private val _contributors = MutableStateFlow<List<Contributor>>(initialContributors)
    val contributors: StateFlow<List<Contributor>> = _contributors.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    @Volatile
    private var hasFetched = false

    fun refreshIfNeeded(targetUrlOrRepo: String = DEFAULT_REPO_URL) {
        if (hasFetched || _isLoading.value) return
        scope.launch {
            _isLoading.value = true
            val remote = fetchFromGitHub(targetUrlOrRepo)
            if (remote.isNotEmpty()) {
                _contributors.value = initialContributors + remote
                hasFetched = true
            }
            _isLoading.value = false
        }
    }

    suspend fun fetchFromGitHub(targetUrlOrRepo: String = DEFAULT_REPO_URL): List<Contributor> =
        withContext(Dispatchers.IO) {
            val ownerRepo = targetUrlOrRepo
                .removePrefix("https://github.com/")
                .removePrefix("http://github.com/")
                .trimEnd('/')

            val apiUrl = "https://api.github.com/repos/$ownerRepo/contributors"
            val request = Request.Builder()
                .url(apiUrl)
                .header("User-Agent", "SukiWalls-Android-App")
                .build()

            try {
                httpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) return@withContext emptyList()
                    val body = response.body?.string() ?: return@withContext emptyList()
                    val jsonArray = JSONArray(body)
                    val result = mutableListOf<Contributor>()
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val login = obj.optString("login")
                        val avatarUrl = obj.optString("avatar_url")
                        val htmlUrl = obj.optString("html_url")
                        result.add(
                            Contributor(
                                name = login,
                                role = "Open Source Contributor",
                                github = htmlUrl,
                                bio = "Contributed to SukiWalls repository.",
                                avatarLetter = login.take(1).uppercase(),
                                avatarUrl = avatarUrl,
                            )
                        )
                    }
                    result
                }
            } catch (_: Exception) {
                emptyList()
            }
        }
}
