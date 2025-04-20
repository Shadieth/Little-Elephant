package com.example.littleelephant.apiRest

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

data class UserResponse(
    val email: String,
    val unlockedLevels: List<Int>
)

data class UnlockLevelRequest(
    val level: Int
)

data class UnlockLevelResponse(
    val message: String,
    val unlockedLevels: List<Int>
)

data class UpdateUserRequest(
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val gender: String,
    val password: String? = null,
    val currentPassword: String? = null
)

data class UserResponseUpdate(
    val firstName: String?,
    val lastName: String?,
    val birthDate: String?,
    val gender: String?,
    val email: String,
    val unlockedLevels: List<Int>
)

interface ApiService {

    @POST("users")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("users/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("ecosystems") // Ajusta si tiene prefijo
    suspend fun getEcosystems(): List<Ecosystem>

    @GET("users/by-email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): UserResponse

    @POST("users/{email}/unlock-level")
    suspend fun unlockLevel(
        @Path("email") email: String,
        @Body request: UnlockLevelRequest
    ): UnlockLevelResponse

    @GET("users/{email}")
    suspend fun getUserByEmailForProfile(@Path("email") email: String): UserResponseUpdate

    @PUT("users/by-email/{email}")
    suspend fun updateUser(
        @Path("email") email: String,
        @Body request: UpdateUserRequest
    ): UserResponseUpdate

}
