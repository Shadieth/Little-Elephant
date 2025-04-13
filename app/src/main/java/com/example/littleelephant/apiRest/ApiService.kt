package com.example.littleelephant.apiRest

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//Register request and response
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

//Login request and response
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean
)

data class Ecosystem(
    val name: String,
    val image: String,
    val questions: List<Question>
)

data class Question(
    val image: String,
    val options: List<String>,
    val correctAnswer: String
)

interface ApiService {

    @POST("users")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("users/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("ecosystems") // Ajusta si tiene prefijo
    suspend fun getEcosystems(): List<Ecosystem>
}
