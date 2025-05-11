package com.example.littleelephant.naviagtion

import android.content.Context
import androidx.core.content.edit

/**
 * Objeto que gestiona la sesión del usuario.
 * Permite guardar, obtener y borrar el email del usuario en las preferencias compartidas.
 */
object UserSessionManager {

    private const val PREFS_USER_DATA = "user_data"
    private const val PREFS_USER_SESSION = "user_prefs"
    private const val KEY_EMAIL = "email"

    /**
     * Guarda el email del usuario en las preferencias compartidas.
     *
     * @param context Contexto de la aplicación.
     * @param email Email del usuario a guardar.
     */
    fun saveEmail(context: Context, email: String) {
        context.getSharedPreferences(PREFS_USER_DATA, Context.MODE_PRIVATE).edit {
            putString(KEY_EMAIL, email)
        }
    }

    /**
     * Obtiene el email del usuario almacenado en las preferencias compartidas.
     *
     * @param context Contexto de la aplicación.
     * @return El email del usuario o null si no existe.
     */
    fun getEmail(context: Context): String? {
        return context.getSharedPreferences(PREFS_USER_DATA, Context.MODE_PRIVATE)
            .getString(KEY_EMAIL, null)
    }

    /**
     * Borra la sesión del usuario eliminando los datos almacenados en las preferencias compartidas.
     *
     * @param context Contexto de la aplicación.
     */
    fun clearSession(context: Context) {
        context.getSharedPreferences(PREFS_USER_SESSION, Context.MODE_PRIVATE).edit {
            clear()
        }
    }
}

