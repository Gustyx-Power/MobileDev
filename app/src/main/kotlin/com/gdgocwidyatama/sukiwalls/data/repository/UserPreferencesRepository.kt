package com.gdgocwidyatama.sukiwalls.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore(name = "suki_user_prefs")

enum class AppThemeMode {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}

class UserPreferencesRepository private constructor(private val context: Context) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val KEY_SETUP_COMPLETED = booleanPreferencesKey("is_setup_completed")
    private val KEY_THEME_MODE = stringPreferencesKey("theme_mode")

    val isSetupCompleted: StateFlow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[KEY_SETUP_COMPLETED] ?: false }
        .stateIn(scope, SharingStarted.Eagerly, false)

    val themeMode: StateFlow<AppThemeMode> = context.dataStore.data
        .map { prefs ->
            val raw = prefs[KEY_THEME_MODE] ?: AppThemeMode.FOLLOW_SYSTEM.name
            runCatching { AppThemeMode.valueOf(raw) }.getOrDefault(AppThemeMode.FOLLOW_SYSTEM)
        }
        .stateIn(scope, SharingStarted.Eagerly, AppThemeMode.FOLLOW_SYSTEM)

    fun setSetupCompleted(completed: Boolean) {
        scope.launch {
            context.dataStore.edit { prefs ->
                prefs[KEY_SETUP_COMPLETED] = completed
            }
        }
    }

    fun setThemeMode(mode: AppThemeMode) {
        scope.launch {
            context.dataStore.edit { prefs ->
                prefs[KEY_THEME_MODE] = mode.name
            }
        }
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
