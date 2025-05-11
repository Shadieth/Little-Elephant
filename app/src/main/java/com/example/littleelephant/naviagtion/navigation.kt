package com.example.littleelephant.naviagtion

/**
 * Clase sellada que define todas las pantallas de la aplicación y sus respectivas rutas.
 * Cada pantalla se representa como un objeto de la clase `AppScreens`.
 */
sealed class AppScreens(val route: String) {

    /**
     * Pantalla de inicio de sesión.
     * Ruta: "login_screen"
     */
    object LoginScreen : AppScreens("login_screen")

    /**
     * Pantalla de ecosistemas.
     * Ruta: "ecosystems_screen"
     */
    object EcosystemsScreen : AppScreens("ecosystems_screen")

    /**
     * Pantalla de registro de usuario.
     * Ruta: "register_screen"
     */
    object RegisterScreen : AppScreens("register_screen")

    /**
     * Pantalla de perfil del usuario.
     * Ruta: "profile_screen"
     */
    object ProfileScreen : AppScreens("profile_screen")

    /**
     * Pantalla de éxito del registro.
     * Ruta: "registration_success"
     */
    object RegistrationSuccess : AppScreens("registration_success")

    /**
     * Pantalla de flujo de preguntas.
     * Esta pantalla recibe un parámetro de ruta llamado "ecosystemName".
     * Ruta base: "question_screen/{ecosystemName}"
     */
    object QuestionScreen : AppScreens("question_screen/{ecosystemName}") {

        /**
         * Función para crear la ruta completa con el parámetro "ecosystemName".
         *
         * @param ecosystemName Nombre del ecosistema.
         * @return Ruta completa con el nombre del ecosistema.
         */
        fun createRoute(ecosystemName: String) = "question_screen/$ecosystemName"
    }

    /**
     * Pantalla de progreso del usuario.
     * Ruta: "progress_screen"
     */
    object ProgressScreen : AppScreens("progress_screen")
}
