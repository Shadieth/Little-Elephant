package com.example.littleelephant.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader

object TranslationManager {

    private var translations: JSONObject? = null

    /**
     * Cargar el idioma desde el archivo JSON
     */
    suspend fun loadLanguage(context: Context, langCode: String) {
        val fileName = when (langCode) {
            "en" -> "strings_en.json"
            else -> "strings_es.json"
        }

        val jsonString = withContext(Dispatchers.IO) {
            context.assets.open(fileName).use { inputStream ->
                BufferedReader(inputStream.reader()).use { it.readText() }
            }
        }

        translations = JSONObject(jsonString)
    }

    /**
     * Obtener un texto traducido
     */
    fun getString(key: String): String {
        return translations?.optString(key) ?: key
    }
}





