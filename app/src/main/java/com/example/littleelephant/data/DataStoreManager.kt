package com.example.littleelephant.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {
    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    val language: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LANGUAGE_KEY] ?: "es" // Por defecto español
    }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { settings ->
            settings[LANGUAGE_KEY] = lang
        }
    }
}
