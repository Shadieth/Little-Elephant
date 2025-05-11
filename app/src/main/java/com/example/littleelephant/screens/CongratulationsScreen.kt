package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.littleelephant.R
import com.example.littleelephant.data.TranslationManager
import com.example.littleelephant.data.dataStore
import kotlinx.coroutines.flow.first

@Composable
fun CongratulationsScreen(onBackToHome: () -> Unit) {

    val context = LocalContext.current

    // Variables mutables para los textos que se actualizan al cambiar el idioma.
    var titleText by remember { mutableStateOf(TranslationManager.getString("congrats_title")) }
    var messageText by remember { mutableStateOf(TranslationManager.getString("congrats_message")) }
    var backHomeText by remember { mutableStateOf(TranslationManager.getString("back_home")) }

    /**
     * Función para actualizar los textos según el idioma seleccionado.
     */
    fun updateTexts() {
        titleText = TranslationManager.getString("congrats_title")
        messageText = TranslationManager.getString("congrats_message")
        backHomeText = TranslationManager.getString("back_home")
    }

    /**
     * Cargar el idioma inicial al entrar a la pantalla.
     */
    LaunchedEffect(Unit) {
        val lang = context.dataStore.data.first()[stringPreferencesKey("language")] ?: "es"
        TranslationManager.loadLanguage(context, lang)
        updateTexts()
    }

    /**
     * Fondo degradado de la pantalla.
     */
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE0F7FA), // azul agua claro
            Color(0xFFF1F8E9), // verde pastel
            Color(0xFFFFF3E0)  // crema cálido
        )
    )

    /**
     * Degradado del botón.
     */
    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF5C5D83), // azul lavanda profundo
            Color(0xFF6A6B96)  // lavanda suave brillante
        )
    )

    // Estructura de la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Imagen de felicitaciones
            Image(
                painter = painterResource(id = R.drawable.elefanteconglobo),
                contentDescription = "Felicitaciones",
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Fit
            )

            // Título de felicitación
            Text(
                text = titleText,
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF4A148C)
            )

            // Mensaje de felicitación
            Text(
                text = messageText,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            // Botón "Volver a inicio"
            Button(
                onClick = onBackToHome,
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(50), clip = true),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = buttonGradient,
                            shape = RoundedCornerShape(50)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = backHomeText,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CongratulationsScreenPreview() {
    CongratulationsScreen(onBackToHome = {})
}


