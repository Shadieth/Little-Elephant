/**
 * UserRepository.kt
 *
 * Repositorio responsable de gestionar las operaciones relacionadas con el usuario,
 * incluyendo el registro y login, utilizando Retrofit para las solicitudes HTTP.
 */

package com.example.littleelephant.apiRest

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * UserRepository - Clase que gestiona las operaciones de registro y login de usuarios.
 */
class UserRepository {

    /**
     * Realiza una solicitud para registrar un nuevo usuario en el backend.
     *
     * @param request Datos del usuario a registrar encapsulados en un objeto RegisterRequest.
     * @param callback Función que recibe un objeto Result con el resultado de la operación.
     * Si la operación es exitosa, el objeto RegisterResponse será entregado.
     * En caso de error, se entregará una excepción.
     */
    fun registerUser(request: RegisterRequest, callback: (Result<RegisterResponse>) -> Unit) {
        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<RegisterResponse> {

            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(Result.success(it)) // Respuesta exitosa, se entrega el objeto RegisterResponse.
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

    /**
     * Realiza una solicitud de inicio de sesión.
     *
     * @param request Datos de acceso encapsulados en un objeto LoginRequest.
     * @param callback Función que recibe un objeto Result con el resultado de la operación.
     * Si la operación es exitosa, se entrega un objeto LoginResponse.
     * En caso de error, se entregará una excepción.
     */
    fun loginUser(request: LoginRequest, callback: (Result<LoginResponse>) -> Unit) {
        RetrofitClient.instance.loginUser(request).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.d("LoginResponse", "Response: ${response.body()}") // Registro de respuesta para depuración

                    response.body()?.let {
                        if (it.success) {
                            callback(Result.success(it)) // Login exitoso
                        } else {
                            callback(Result.failure(Exception("Credenciales incorrectas")))
                        }
                    } ?: callback(Result.failure(Exception("Respuesta vacía del servidor.")))
                } else {
                    callback(Result.failure(Exception("Error en la respuesta del servidor: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginError", "Error de red o conexión: ${t.message}", t) // Registro del error
                callback(Result.failure(Exception("Error de red o conexión: ${t.message}")))
            }
        })
    }
}



