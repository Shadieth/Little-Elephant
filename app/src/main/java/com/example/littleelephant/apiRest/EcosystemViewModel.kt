/**
 * EcosystemViewModel.kt
 *
 * ViewModel para gestionar los datos relacionados con los ecosistemas y los niveles desbloqueados del usuario.
 * Permite obtener los ecosistemas disponibles, los niveles desbloqueados del usuario y desbloquear nuevos niveles.
 */

package com.example.littleelephant.apiRest

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * EcosystemViewModel - ViewModel que gestiona los datos de los ecosistemas y niveles desbloqueados.
 */
class EcosystemViewModel : ViewModel() {

    // Estado que contiene la lista de ecosistemas obtenidos del backend.
    private val _ecosystems = mutableStateOf<List<Ecosystem>>(emptyList())
    val ecosystems: State<List<Ecosystem>> = _ecosystems

    // Estado que contiene los niveles desbloqueados por el usuario.
    private val _unlockedLevels = mutableStateOf<List<Int>>(emptyList())
    val unlockedLevels: State<List<Int>> = _unlockedLevels

    /**
     * Inicializa el ViewModel y carga los ecosistemas al iniciar.
     * Se realiza una llamada a la API para obtener la lista de ecosistemas disponibles.
     */
    init {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getEcosystems()
                _ecosystems.value = response
            } catch (e: Exception) {
                Log.e("API", "Error fetching ecosystems", e)
            }
        }
    }

    /**
     * fetchUserByEmail - Obtiene los niveles desbloqueados del usuario a partir de su email.
     *
     * @param email Email del usuario para identificarlo en la base de datos.
     */
    fun fetchUserByEmail(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getUserByEmail(email)
                _unlockedLevels.value = response.unlockedLevels
            } catch (e: Exception) {
                Log.e("API", "Error fetching user data", e)
            }
        }
    }

    /**
     * unlockLevel - Desbloquea un nivel específico para el usuario.
     *
     * @param email Email del usuario para identificarlo en la base de datos.
     * @param level Nivel a desbloquear.
     */
    fun unlockLevel(email: String, level: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.unlockLevel(
                    email,
                    UnlockLevelRequest(level)
                )
                _unlockedLevels.value = response.unlockedLevels
            } catch (e: Exception) {
                Log.e("API", "Error desbloqueando nivel", e)
            }
        }
    }
}


