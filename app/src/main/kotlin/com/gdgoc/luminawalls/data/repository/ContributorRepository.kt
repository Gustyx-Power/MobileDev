package com.gdgoc.luminawalls.data.repository

import com.gdgoc.luminawalls.data.model.Contributor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ContributorRepository {

    // TODO: [Session 4] Masukkan URL repositori GitHub LuminaWalls kamu di bawah sini:



    const val DEFAULT_REPO_URL = "https://github.com/gdgocwidyatama/MobileDev"

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _contributors = MutableStateFlow<List<Contributor>>(emptyList())
    val contributors: StateFlow<List<Contributor>> = _contributors.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    @Volatile
    private var hasFetched = false

    fun refreshIfNeeded(targetUrlOrRepo: String = DEFAULT_REPO_URL) {
        if (hasFetched || _isLoading.value) return
        scope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _contributors.value = fetchFromGitHub(targetUrlOrRepo)
                hasFetched = true
            } catch (e: Exception) {
                _error.value = e.message ?: "Gagal memuat contributor"
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun fetchFromGitHub(targetUrlOrRepo: String = DEFAULT_REPO_URL): List<Contributor> =
        withContext(Dispatchers.IO) {

            // TODO: [Session 4] Eksekusi REST API GitHub untuk mengambil daftar kontributor di bawah sini:



            emptyList()
        }
}
