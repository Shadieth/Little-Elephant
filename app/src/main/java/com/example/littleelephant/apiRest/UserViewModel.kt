package com.example.littleelephant.apiRest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
    fun registerUser(request: RegisterRequest) {
        userRepository.registerUser(request) { result ->
            result.onSuccess {
                _registrationSuccess.value = "Usuario registrado: ${it.firstName}"
            }.onFailure {
                _registrationError.value = "Error: ${it.message}"
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

}



