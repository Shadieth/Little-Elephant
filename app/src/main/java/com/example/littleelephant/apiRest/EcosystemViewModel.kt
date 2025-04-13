package com.example.littleelephant.apiRest

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EcosystemViewModel : ViewModel() {
    private val _ecosystems = mutableStateOf<List<Ecosystem>>(emptyList())
    val ecosystems: State<List<Ecosystem>> = _ecosystems

    private val _unlockedLevels = mutableStateOf<List<Int>>(emptyList())
    val unlockedLevels: State<List<Int>> = _unlockedLevels

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

