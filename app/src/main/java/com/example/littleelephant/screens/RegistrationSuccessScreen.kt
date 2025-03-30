package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
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

@Composable
fun RegistrationSuccessScreen(navController: NavController) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp // Obtiene la altura de la pantalla
    val imageHeight = screenHeight * 0.4f // Establece el porcentaje del tamaño de la pantalla para la imagen

    val courgetteFont = FontFamily(
        Font(R.font.courgetteregular) // Usando el archivo Courgette-Regular.ttf
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF7AB9D7), Color(0xFF57A3CC)) // Fondo rosa con gradiente
                )
            )
    ) {
        // Contenido principal, centrado
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Texto en la parte superior
            Text(
                text = "¡Usuario creado correctamente!",
                style = TextStyle(
                    fontFamily = courgetteFont, // Aplica la fuente Courgette Regular
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp, // Tamaño de la fuente
                    color = Color(0xFFD7E9ED), // Color del texto
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .graphicsLayer(
                        shadowElevation = 8.dp.value, // Elevación de la sombra
                        shape = RoundedCornerShape(16.dp), // Esquinas redondeadas
                        clip = false
                    )
                    .background(
                        color = Color(0xFF4F9BC4), // Fondo blanco
                        shape = RoundedCornerShape(16.dp) // Esquinas redondeadas para el fondo
                    )
                    .border(
                        width = 2.dp, // Borde de 2 dp
                        color = Color(0xFF519FC9), // Color del borde
                        shape = RoundedCornerShape(16.dp) // Borde con esquinas redondeadas
                    )
                    .padding(12.dp) // Añadimos un padding para separar el texto del borde
            )


            // Imagen centrada, ajustada al tamaño de la pantalla
            Image(
                painter = painterResource(id = R.drawable.elefantegirl), // Reemplaza 'elefantegirl' con el nombre de tu imagen
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()  // La imagen ocupará todo el ancho
                    .height(imageHeight)  // Ajusta la altura de la imagen al 40% de la altura de la pantalla
                    .padding(bottom = 32.dp) // Añade espacio debajo de la imagen
            )

            // Botón de "Iniciar sesión"
            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("registration_success") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // Lo dejamos transparente para aplicar el brush
                    contentColor = Color(0xFFD7E9ED)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(50), clip = true) // Agregar sombra
                    .background(
                        brush = Brush.horizontalGradient( // Gradiente horizontal para el fondo
                            colors = listOf(Color(0xFFD55F36), Color(0xFFFF7043))
                        ),
                        shape = RoundedCornerShape(50) // Redondeamos el botón
                    )
            ) {
                Text(text = "Iniciar sesión",
                    style = TextStyle(
                        fontSize = 18.sp, // Tamaño de la fuente
                        fontWeight = FontWeight.Bold, // Hacer el texto en negrita
                        color = Color.White // Color del texto
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

