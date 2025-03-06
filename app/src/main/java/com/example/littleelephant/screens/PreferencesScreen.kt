package com.example.littleelephant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(navController: NavHostController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Preferencias", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF395173) // Color del fondo de la barra
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = Color(0xFFFACDDD)) // Fondo de la pantalla
        ) {
            // Aquí puedes añadir el contenido de la pantalla
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPreferencesScreen() {
    PreferencesScreen()
}






