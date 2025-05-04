package com.example.littleelephant.naviagtion

import android.content.Context
import androidx.core.content.edit

object UserSessionManager {
    fun saveEmail(context: Context, email: String) {
        context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
            .edit() { putString("email", email) }
    }

    fun getEmail(context: Context): String? {
        return context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
            .getString("email", null)
    }

    fun clearSession(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit() { clear() }
    }

}
