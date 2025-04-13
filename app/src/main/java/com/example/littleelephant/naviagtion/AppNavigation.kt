package com.example.littleelephant.naviagtion

import com.example.littleelephant.screens.RegistrationSuccessScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littleelephant.screens.EcosystemsScreen
import com.example.littleelephant.screens.LoginScreen
import com.example.littleelephant.screens.PreferencesScreen
import com.example.littleelephant.screens.QuestionFlowScreen
import com.example.littleelephant.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.EcosystemsScreen.route) {
            EcosystemsScreen(navController)
        }
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(AppScreens.PreferencesScreen.route) {
            PreferencesScreen(navController)
        }
        composable(AppScreens.RegistrationSuccess.route) {
            RegistrationSuccessScreen(navController)
        }
        composable(AppScreens.QuestionScreen.route) { backStackEntry ->
            val ecosystemName = backStackEntry.arguments?.getString("ecosystemName") ?: ""
            QuestionFlowScreen(navController = navController, ecosystemName = ecosystemName)
        }

    }
}

