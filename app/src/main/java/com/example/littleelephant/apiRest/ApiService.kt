package com.example.littleelephant.apiRest

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val birthDate: String,  // Aquí es un String, para el formato de fecha "YYYY-MM-DD"
    val gender: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: String
)

interface ApiService {
    @POST("users")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>
}
