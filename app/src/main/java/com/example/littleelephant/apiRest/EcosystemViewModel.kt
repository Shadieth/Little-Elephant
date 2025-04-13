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
}

