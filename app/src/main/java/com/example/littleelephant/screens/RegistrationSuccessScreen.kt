package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littleelephant.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.littleelephant.data.TranslationManager
import com.example.littleelephant.data.dataStore
import kotlinx.coroutines.flow.first

@Composable
fun RegistrationSuccessScreen(navController: NavController) {

    val context = LocalContext.current

    // --- VARIABLES DE TEXTO (TRADUCCIÓN) ---
    var registrationSuccessText by remember { mutableStateOf(TranslationManager.getString("registration_success")) }
    var loginText by remember { mutableStateOf(TranslationManager.getString("login")) }

    /**
     * Función para actualizar los textos según el idioma seleccionado.
     */
    fun updateTexts() {
        registrationSuccessText = TranslationManager.getString("registration_success")
        loginText = TranslationManager.getString("login")
    }

    /**
     * Cargar el idioma inicial al entrar a la pantalla.
     */
    LaunchedEffect(Unit) {
        val lang = context.dataStore.data.first()[stringPreferencesKey("language")] ?: "es"
        TranslationManager.loadLanguage(context, lang)
        updateTexts()
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = screenHeight * 0.4f

    val courgetteFont = FontFamily(
        Font(R.font.courgetteregular)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFC5CAE9), Color(0xFFE1BEE7), Color(0xFFFCE4EC))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = registrationSuccessText,
                style = TextStyle(
                    fontFamily = courgetteFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFF616583),
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(12.dp),
            )

            Image(
                painter = painterResource(id = R.drawable.elefantegirl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .padding(bottom = 32.dp)
            )

            Button(
                onClick = {
                    navController.navigate("login_screen") {
                        popUpTo("registration_success") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFD7E9ED)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(50), clip = true)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF696D8A), Color(0xFF616583))
                        ),
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Text(
                    text = loginText,
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

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationSuccessScreen() {
    val navController = rememberNavController()
    RegistrationSuccessScreen(navController = navController)
}

