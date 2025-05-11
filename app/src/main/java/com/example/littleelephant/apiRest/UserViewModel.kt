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
import retrofit2.HttpException

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()

    // --- REGISTRO ---

    private val _registrationSuccess = MutableLiveData<String>()
    val registrationSuccess: LiveData<String> get() = _registrationSuccess

    private val _registrationError = MutableLiveData<String>()
    val registrationError: LiveData<String> get() = _registrationError

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
                val errorMessage = "Error al registrar: ${it.message}"
                println(errorMessage)
                _registrationError.value = errorMessage
                onFailure(it.message ?: "Error desconocido")
            }
        }
    }

    // --- LOGIN ---

    private val _loginSuccess = MutableLiveData<String?>()
    val loginSuccess: LiveData<String?> get() = _loginSuccess

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> get() = _loginError

    fun loginUser(request: LoginRequest) {
        userRepository.loginUser(request) { result ->
            result.onSuccess { loginResponse ->
                if (loginResponse.success) {
                    _loginSuccess.value = "Inicio de sesión exitoso"
                } else {
                    _loginError.value = "Credenciales incorrectas"
                }
            }.onFailure { exception ->
                val errorMessage = "Error al iniciar sesión: ${exception.message}"
                println(errorMessage)
                _loginError.value = errorMessage
            }
        }
    }

    fun clearLoginState() {
        _loginSuccess.value = null
        _loginError.value = null
    }

    // --- PERFIL DE USUARIO ---

    val userData = mutableStateOf<UserResponseUpdate?>(null)

    fun fetchUserByEmailForProfile(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getUserByEmailForProfile(email)
                Log.d("UserViewModel", "Perfil cargado: $response")
                userData.value = response
            } catch (e: Exception) {
                val errorMessage = "Error al obtener perfil: ${e.message}"
                println(errorMessage)
                Log.e("UserViewModel", errorMessage)
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
                val errorMessage = "Error al actualizar perfil: ${e.message}"
                println(errorMessage)
                onError(errorMessage)
            }
        }
    }

    // --- GESTIÓN DE USUARIOS ---

    fun checkUserExists(
        email: String,
        onResult: (Boolean) -> Unit,
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.checkUserExists(email)
                println("Respuesta del backend: $response")

                // Accedemos directamente al valor de "exists"
                val userExists = response["exists"] ?: false
                onResult(userExists)

            } catch (e: HttpException) {
                println("Error HTTP: ${e.code()} - ${e.message()}")
                onError("Error al verificar usuario: ${e.message()}")
            } catch (e: Exception) {
                println("Error general: ${e.message}")
                onError("Error al verificar usuario: ${e.message}")
            }
        }
    }

    fun deleteUser(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.deleteUserByEmail(email)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    val errorMessage = "No se pudo eliminar el usuario. Código: ${response.code()}"
                    println(errorMessage)
                    onError(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = "Error al eliminar usuario: ${e.message}"
                println(errorMessage)
                onError(errorMessage)
            }
        }
    }

    // --- GESTIÓN DE ECOSISTEMAS ---

    private val _ecosystems = MutableStateFlow<List<Ecosystem>>(emptyList())
    val ecosystems: StateFlow<List<Ecosystem>> = _ecosystems

    fun fetchAllEcosystems() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.instance.getEcosystems()
                _ecosystems.value = result
            } catch (e: Exception) {
                val errorMessage = "Error al obtener ecosistemas: ${e.message}"
                println(errorMessage)
            }
        }
    }
}



