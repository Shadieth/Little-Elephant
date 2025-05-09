package com.example.littleelephant.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object TranslationManager {
    private var translations: JSONObject? = null

    private val _isTranslationLoaded = MutableStateFlow(false)
    val isTranslationLoaded: StateFlow<Boolean> get() = _isTranslationLoaded

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
        _isTranslationLoaded.value = true
    }

    fun getString(key: String): String {
        return translations?.optString(key) ?: key
    }
}



