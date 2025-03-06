package com.example.littleelephant.naviagtion

sealed class AppScreens(val route: String) {
    object FirstScreen : AppScreens("first_screen")
    object SecondScreen : AppScreens("second_screen")
    object RegisterScreen : AppScreens("register_screen")
    object PreferencesScreen : AppScreens("preferences_screen")
}