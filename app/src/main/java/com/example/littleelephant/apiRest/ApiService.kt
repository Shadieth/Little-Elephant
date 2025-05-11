package com.example.littleelephant.apiRest

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.DELETE

// --- DATA CLASSES ---

// --- Registro: Request y Response ---
/**
 * Datos necesarios para el registro de un usuario.
 */
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val birthDate: String,  // Formato: "YYYY-MM-DD"
    val gender: String,
    val email: String,
    val password: String
)

/**
 * Respuesta del backend tras el registro exitoso.
 */
data class RegisterResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: String
)

// --- Login: Request y Response ---
/**
 * Datos necesarios para iniciar sesión.
 */
data class LoginRequest(
    val email: String,
    val password: String
)

/**
 * Respuesta del backend tras el intento de login.
 */
data class LoginResponse(
    val success: Boolean
)

// --- Ecosistemas y Preguntas ---
/**
 * Representación de un ecosistema.
 */
data class Ecosystem(
    val name: String,
    val image: String,
    val questions: List<Question>
)

/**
 * Representación de una pregunta asociada a un ecosistema.
 */
data class Question(
    val image: String,
    val options: List<String>,
    val correctAnswer: String
)

// --- Respuesta de Usuario ---
/**
 * Respuesta con los datos básicos del usuario.
 */
data class UserResponse(
    val email: String,
    val unlockedLevels: List<Int>
)

// --- Desbloqueo de Niveles ---
/**
 * Request para desbloquear un nivel.
 */
data class UnlockLevelRequest(
    val level: Int
)

/**
 * Respuesta tras intentar desbloquear un nivel.
 */
data class UnlockLevelResponse(
    val message: String,
    val unlockedLevels: List<Int>
)

// --- Actualización del Usuario ---
/**
 * Request para actualizar los datos del usuario.
 */
data class UpdateUserRequest(
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val gender: String,
    val password: String? = null,  // Opcional, solo se envía si se desea cambiar la contraseña
    val currentPassword: String? = null  // Opcional, requerido si se envía un nuevo password
)

/**
 * Respuesta tras actualizar los datos del usuario.
 */
data class UserResponseUpdate(
    val firstName: String?,
    val lastName: String?,
    val birthDate: String?,
    val gender: String?,
    val email: String,
    val unlockedLevels: List<Int>
)

// --- INTERFAZ DEL API SERVICE ---
interface ApiService {

    // --- REGISTRO DE USUARIO ---

    /**
     * Endpoint para registrar un nuevo usuario.
     */
    @POST("users")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    // --- LOGIN DE USUARIO ---

    /**
     * Endpoint para iniciar sesión.
     */
    @POST("users/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    // --- GESTIÓN DE ECOSISTEMAS ---

    /**
     * Endpoint para obtener todos los ecosistemas.
     */
    @GET("ecosystems")
    suspend fun getEcosystems(): List<Ecosystem>

    // --- OBTENER USUARIO POR EMAIL ---

    /**
     * Endpoint para obtener los datos básicos del usuario por email.
     */
    @GET("users/by-email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): UserResponse

    /**
     * Endpoint para obtener el perfil completo del usuario.
     */
    @GET("users/{email}")
    suspend fun getUserByEmailForProfile(@Path("email") email: String): UserResponseUpdate

    @GET("users/exists/{email}")
    suspend fun checkUserExists(@Path("email") email: String): Map<String, Boolean>

    // --- ACTUALIZACIÓN DEL USUARIO ---

    /**
     * Endpoint para actualizar los datos del usuario.
     */
    @PUT("users/by-email/{email}")
    suspend fun updateUser(
        @Path("email") email: String,
        @Body request: UpdateUserRequest
    ): UserResponseUpdate

    // --- DESBLOQUEO DE NIVELES ---

    /**
     * Endpoint para desbloquear un nivel para un usuario específico.
     */
    @POST("users/{email}/unlock-level")
    suspend fun unlockLevel(
        @Path("email") email: String,
        @Body request: UnlockLevelRequest
    ): UnlockLevelResponse

    // --- ELIMINACIÓN DE USUARIO ---

    /**
     * Endpoint para eliminar un usuario por email.
     */
    @DELETE("users/{email}")
    suspend fun deleteUserByEmail(@Path("email") email: String): Response<Unit>
}

