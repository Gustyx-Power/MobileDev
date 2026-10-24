package com.gdgoc.luminawalls.data.repository

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// TODO: [Session 3] Ganti in-memory state ini dengan Jetpack DataStore Preferences
enum class AppThemeMode {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}

class UserPreferencesRepository private constructor(private val context: Context) {

    // TODO: [Session 3] Ganti in-memory state ini dengan Jetpack DataStore Preferences di bawah sini:



    private val _isSetupCompleted = MutableStateFlow(false)
    val isSetupCompleted: StateFlow<Boolean> = _isSetupCompleted.asStateFlow()

    private val _themeMode = MutableStateFlow(AppThemeMode.FOLLOW_SYSTEM)
    val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

    fun setSetupCompleted(completed: Boolean) {
        _isSetupCompleted.value = completed
    }

    fun setThemeMode(mode: AppThemeMode) {
        _themeMode.value = mode
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferencesRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
