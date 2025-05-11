package com.example.littleelephant.naviagtion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littleelephant.screens.EcosystemsScreen
import com.example.littleelephant.screens.LoginScreen
import com.example.littleelephant.screens.ProfileScreen
import com.example.littleelephant.screens.ProgressScreen
import com.example.littleelephant.screens.QuestionFlowScreen
import com.example.littleelephant.screens.RegisterScreen
import com.example.littleelephant.screens.RegistrationSuccessScreen

/**
 * Función composable que define la estructura de navegación de la aplicación.
 * Contiene todas las pantallas y sus respectivas rutas.
 */
@Composable
fun AppNavigation() {

    // Controlador de navegación
    val navController = rememberNavController()

    /**
     * Definición del NavHost que contiene todas las pantallas de la aplicación.
     * La pantalla de inicio es `LoginScreen`.
     */
    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {

        /**
         * Pantalla de Login.
         * Pasa el controlador de navegación a la pantalla.
         */
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }

        /**
         * Pantalla de Ecosistemas.
         * Pasa el controlador de navegación a la pantalla.
         */
        composable(AppScreens.EcosystemsScreen.route) {
            EcosystemsScreen(navController)
        }

        /**
         * Pantalla de Registro.
         * Pasa el controlador de navegación a la pantalla.
         */
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }

        /**
         * Pantalla de Perfil del Usuario.
         * Pasa el controlador de navegación a la pantalla.
         */
        composable(AppScreens.ProfileScreen.route) {
            ProfileScreen(navController)
        }

        /**
         * Pantalla de Éxito del Registro.
         * Pasa el controlador de navegación a la pantalla.
         */
        composable(AppScreens.RegistrationSuccess.route) {
            RegistrationSuccessScreen(navController)
        }

        /**
         * Pantalla del Flujo de Preguntas.
         * Recibe el nombre del ecosistema como parámetro de navegación.
         */
        composable(AppScreens.QuestionScreen.route) { backStackEntry ->
            val ecosystemName = backStackEntry.arguments?.getString("ecosystemName") ?: ""
            QuestionFlowScreen(navController = navController, ecosystemName = ecosystemName)
        }

        /**
         * Pantalla de Progreso del Usuario.
         * Pasa el controlador de navegación y el ViewModel a la pantalla.
         */
        composable(AppScreens.ProgressScreen.route) {
            ProgressScreen(
                navController = navController,
                viewModel = viewModel()
            )
        }
    }
}


