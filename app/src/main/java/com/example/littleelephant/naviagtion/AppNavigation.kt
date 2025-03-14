package com.example.littleelephant.naviagtion

import SecondScreenLevels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littleelephant.screens.FirstScreenLogin
import com.example.littleelephant.screens.PreferencesScreen
import com.example.littleelephant.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.FirstScreen.route) {
        composable(AppScreens.FirstScreen.route) {
            FirstScreenLogin(navController)
        }
        composable(AppScreens.SecondScreen.route) {
            SecondScreenLevels(navController)
        }
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(AppScreens.PreferencesScreen.route) {
            PreferencesScreen(navController)
        }
    }
}
