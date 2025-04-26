package com.example.littleelephant.apiRest

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()

    // Variables para la gestión del registro
    private val _registrationSuccess = MutableLiveData<String>()
    val registrationSuccess: LiveData<String> get() = _registrationSuccess

    private val _registrationError = MutableLiveData<String>()
    val registrationError: LiveData<String> get() = _registrationError

    // Variables para la gestión del login
    private val _loginSuccess = MutableLiveData<String?>()
    val loginSuccess: LiveData<String?> get() = _loginSuccess

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> get() = _loginError

    // Método para registrar usuario
    fun registerUser(
        request: RegisterRequest,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        userRepository.registerUser(request) { result ->
            result.onSuccess {
                _registrationSuccess.value = "Usuario registrado: ${it.firstName}"
                onSuccess()
            }.onFailure {
                _registrationError.value = "Error: ${it.message}"
                onFailure(it.message ?: "Error desconocido")
            }
        }
    }

    // Método para iniciar sesión
    fun loginUser(request: LoginRequest) {
        userRepository.loginUser(request) { result ->
            result.onSuccess { loginResponse ->
                // Ahora loginResponse es un booleano
                if (loginResponse.success) {
                    _loginSuccess.value = "Inicio de sesión exitoso"
                } else {
                    _loginError.value = "Credenciales incorrectas"
                }
            }.onFailure { exception ->
                _loginError.value = "Error: ${exception.message}"
            }
        }
    }

    // Limpiar estado del login
    fun clearLoginState() {
        _loginSuccess.value = null
        _loginError.value = null
    }

    val userData = mutableStateOf<UserResponseUpdate?>(null)

    fun fetchUserByEmailForProfile(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getUserByEmailForProfile(email)
                Log.d("UserViewModel", "Perfil cargado: $response")
                userData.value = response
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error al obtener perfil", e)
            }
        }
    }

    fun updateUserProfile(
        email: String,
        request: UpdateUserRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                RetrofitClient.instance.updateUser(email, request)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error desconocido")
            }
        }
    }

    // Ecosistemas obtenidos desde el backend
    private val _ecosystems = MutableStateFlow<List<Ecosystem>>(emptyList())
    val ecosystems: StateFlow<List<Ecosystem>> = _ecosystems

    fun fetchAllEcosystems() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.instance.getEcosystems()
                _ecosystems.value = result
            } catch (e: Exception) {
                println("Error al obtener ecosistemas: ${e.message}")
            }
        }
    }

}



