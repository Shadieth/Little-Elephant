package com.example.littleelephant.apiRest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _registrationSuccess = MutableLiveData<String>()
    val registrationSuccess: LiveData<String> get() = _registrationSuccess

    private val _registrationError = MutableLiveData<String>()
    val registrationError: LiveData<String> get() = _registrationError

    fun registerUser(request: RegisterRequest) {
        // Llamada al repositorio y manejo del resultado
        userRepository.registerUser(request) { result ->
            result.onSuccess {
                _registrationSuccess.value = "Usuario registrado: ${it.firstName}"
            }.onFailure {
                _registrationError.value = "Error: ${it.message}"
            }
        }
    }
}


