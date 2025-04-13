package com.example.littleelephant.naviagtion

sealed class AppScreens(val route: String) {
    object FirstScreen : AppScreens("login_screen")
    object SecondScreen : AppScreens("second_screen")
    object RegisterScreen : AppScreens("register_screen")
    object PreferencesScreen : AppScreens("preferences_screen")
    object RegistrationSuccess : AppScreens("registration_success")

    // Pantalla de preguntas, con nombre del ecosistema como parámetro
    object QuestionScreen : AppScreens("question_screen/{ecosystemName}") {
        fun createRoute(ecosystemName: String) = "question_screen/$ecosystemName"
    }
}