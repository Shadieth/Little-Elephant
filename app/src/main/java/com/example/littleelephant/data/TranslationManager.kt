package com.example.littleelephant.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader

/**
 * Objeto encargado de gestionar las traducciones de la aplicación.
 * Carga los archivos JSON de traducción y proporciona los textos traducidos a través de claves.
 */
object TranslationManager {

    // Variable que almacena las traducciones cargadas en formato JSON
    private var translations: JSONObject? = null

    /**
     * Cargar el archivo de idioma seleccionado desde los assets.
     *
     * @param context Contexto de la aplicación, necesario para acceder a los assets.
     * @param langCode Código del idioma a cargar ("en" para inglés, "es" para español).
     */
    suspend fun loadLanguage(context: Context, langCode: String) {
        // Determina el nombre del archivo JSON según el idioma
        val fileName = when (langCode) {
            "en" -> "strings_en.json"
            else -> "strings_es.json"
        }

        // Cargar el archivo JSON de forma asíncrona en un contexto de IO
        val jsonString = withContext(Dispatchers.IO) {
            context.assets.open(fileName).use { inputStream ->
                BufferedReader(inputStream.reader()).use { it.readText() }
            }
        }

        // Convertir el contenido del archivo en un objeto JSON
        translations = JSONObject(jsonString)
    }

    /**
     * Obtener el texto traducido asociado a una clave específica.
     *
     * @param key Clave del texto a traducir.
     * @return Texto traducido si existe, de lo contrario devuelve la clave misma.
     */
    fun getString(key: String): String {
        return translations?.optString(key) ?: key
    }
}





