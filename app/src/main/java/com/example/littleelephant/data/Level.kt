package com.example.littleelephant.data

import androidx.compose.ui.graphics.Color

// Data class que define el modelo de un nivel
data class Level(
    val name: String,  // Nombre del nivel
    val location: String, // Ubicación del nivel
    val color: Color, // Color asociado con el nivel
    val imageResId: Int // Propiedad para la imagen del nivel
)
