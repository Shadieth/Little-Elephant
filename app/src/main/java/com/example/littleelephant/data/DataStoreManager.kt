/**
 * DataStoreManager.kt
 *
 * Clase encargada de gestionar el almacenamiento de preferencias de usuario mediante DataStore.
 * En este caso, se utiliza para persistir el idioma seleccionado por el usuario.
 */

package com.example.littleelephant.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión de Context para acceder a DataStore
val Context.dataStore by preferencesDataStore(name = "settings")

/**
 * DataStoreManager - Clase responsable de gestionar las preferencias de usuario mediante DataStore.
 *
 * @param context Contexto de la aplicación necesario para acceder a DataStore.
 */
class DataStoreManager(private val context: Context) {

    companion object {
        // Clave para almacenar el idioma seleccionado
        val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    /**
     * Flow que proporciona el idioma seleccionado por el usuario.
     * Si no se ha seleccionado ningún idioma previamente, el valor por defecto es "es" (español).
     */
    val language: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LANGUAGE_KEY] ?: "es"
    }

    /**
     * Método para actualizar el idioma seleccionado.
     *
     * @param lang Código del idioma a establecer (por ejemplo, "es" o "en").
     */
    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { settings ->
            settings[LANGUAGE_KEY] = lang
        }
    }
}

