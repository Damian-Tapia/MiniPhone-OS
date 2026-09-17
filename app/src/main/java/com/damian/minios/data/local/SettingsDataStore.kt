package com.damian.minios.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Delegate to create DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * SettingsDataStore
 * 
 * A lightweight wrapper around Jetpack Preferences DataStore.
 * Handles persisting user settings like minimalist mode and font size.
 */
class SettingsDataStore(private val context: Context) {

    // Define keys for the preferences we want to store
    companion object {
        val MINIMALIST_MODE_KEY = booleanPreferencesKey("minimalist_mode")
        val FONT_SIZE_MULTIPLIER_KEY = floatPreferencesKey("font_size_multiplier")
    }

    /**
     * minimalistModeFlow
     * 
     * A Flow that emits the current state of Minimalist Mode.
     * Coroutines allow us to react to changes asynchronously.
     */
    val minimalistModeFlow: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            // Handle IOExceptions during data read
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Default to 'false' if the key doesn't exist
            preferences[MINIMALIST_MODE_KEY] ?: false
        }

    /**
     * updateMinimalistMode
     * 
     * Suspend function to update the minimalist mode setting.
     * Must be called from a CoroutineScope.
     */
    suspend fun updateMinimalistMode(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[MINIMALIST_MODE_KEY] = isEnabled
        }
    }

    /**
     * fontSizeMultiplierFlow
     * 
     * A Flow for the font size multiplier setting.
     */
    val fontSizeMultiplierFlow: Flow<Float> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Default to 1.0f (normal size)
            preferences[FONT_SIZE_MULTIPLIER_KEY] ?: 1.0f
        }

    suspend fun updateFontSizeMultiplier(multiplier: Float) {
        context.dataStore.edit { preferences ->
            preferences[FONT_SIZE_MULTIPLIER_KEY] = multiplier
        }
    }
}
