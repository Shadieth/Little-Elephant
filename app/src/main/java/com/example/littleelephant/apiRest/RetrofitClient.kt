/**
 * RetrofitClient.kt
 *
 * Objeto singleton que configura y proporciona una instancia de Retrofit para realizar solicitudes HTTP.
 * Incluye un interceptor para el registro de logs y la conversión de respuestas JSON mediante Gson.
 */

package com.example.littleelephant.apiRest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitClient - Objeto singleton que gestiona la instancia de Retrofit para la comunicación con la API.
 */
object RetrofitClient {

    // URL base del backend. Utiliza el host localhost adaptado para emuladores Android.
    private const val BASE_URL = "http://10.0.2.2:3000/"
    // Alternativa de URL para pruebas locales:
    // private const val BASE_URL = "http://192.168.1.146:3000/"

    /**
     * Interceptor para el registro de logs HTTP.
     * Nivel de registro establecido en BODY para obtener detalles completos de las respuestas.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Cliente HTTP configurado con el interceptor de logs.
     */
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Instancia de Retrofit creada mediante lazy initialization.
     * Utiliza Gson para la conversión de respuestas JSON a objetos Kotlin.
     */
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}


