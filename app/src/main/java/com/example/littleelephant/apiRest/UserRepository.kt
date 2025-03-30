package com.example.littleelephant.apiRest

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun registerUser(request: RegisterRequest, callback: (Result<RegisterResponse>) -> Unit) {
        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(Result.success(it))
                    } ?: callback(Result.failure(Exception("Respuesta vacía del servidor.")))
                } else {
                    callback(Result.failure(Exception("Error: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                callback(Result.failure(Exception("Error de red: ${t.localizedMessage ?: "Desconocido"}")))
            }

        })
    }

    fun loginUser(request: LoginRequest, callback: (Result<LoginResponse>) -> Unit) {
        RetrofitClient.instance.loginUser(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Agregar un log para inspeccionar el cuerpo de la respuesta
                    Log.d("LoginResponse", "Response: ${response.body()}")
                    response.body()?.let {
                        // Aquí solo se espera el valor booleano
                        if (it.success) {
                            callback(Result.success(it))
                        } else {
                            callback(Result.failure(Exception("Credenciales incorrectas")))
                        }
                    } ?: callback(Result.failure(Exception("Respuesta vacía del servidor.")))
                } else {
                    callback(Result.failure(Exception("Error en la respuesta del servidor: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Agrega un log para ver el mensaje de error
                Log.e("LoginError", "Error de red o conexión: ${t.message}", t)
                callback(Result.failure(Exception("Error de red o conexión: ${t.message}")))
            }

        })
    }
}



