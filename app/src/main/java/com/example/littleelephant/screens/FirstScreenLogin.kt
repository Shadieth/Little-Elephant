package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littleelephant.R
import com.example.littleelephant.ui.theme.LittleElephantTheme

@Composable
fun FirstScreenLogin(navController: NavController) {
    Scaffold {
        LittleElephantTheme {
            Content(modifier = Modifier.padding(it).background(color = Color(0xFFFACDDD)), navController)
        }

    }
}

@Composable
fun Content(modifier: Modifier = Modifier, navController: NavController) {
    Column(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.elefantelenguaafuera), // Reemplaza 'your_image_name' con el nombre real de tu imagen en res/drawable
            contentDescription = "Imagen superior",
            modifier = Modifier
                .fillMaxWidth().weight(1f),
            contentScale = ContentScale.Crop // Ajusta la escala de la imagen para llenar el espacio de forma adecuada
        )
        BottomContainer(modifier = Modifier.fillMaxSize().weight(1f), navController)
    }
}

@Composable
fun BottomContainer(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF395173)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Little Elephant",
                color = Color(0xFFFACDDD),
                modifier = Modifier
                    .fillMaxWidth() // Para alinear correctamente el texto
                    .padding(bottom = 20.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            val usuario = remember { mutableStateOf(TextFieldValue()) }

            // Campo de texto para el usuario
            BasicTextField(
                value = usuario.value,
                onValueChange = { usuario.value = it },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
                    .background(Color(0xFFAE3251), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp), // Espaciado interno uniforme
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp,
                    lineHeight = 28.sp // Ajusta la altura de línea para alinearlo con OutlinedTextField
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart // Alinea el texto como en OutlinedTextField
                    ) {
                        if (usuario.value.text.isEmpty()) {
                            Text(
                                text = "Usuario",
                                style = TextStyle(color = Color.White, fontSize = 22.sp)
                            )
                        }
                        innerTextField()
                    }
                },
                singleLine = true,
                cursorBrush = SolidColor(Color.White)
            )

            // Campo de texto para la contraseña
            var passwordState by remember { mutableStateOf(TextFieldState("")) }
            var isPasswordVisible by remember { mutableStateOf(false) }

            OutlinedSecureTextField(
                state = passwordState,
                modifier = Modifier
                    .height(85.dp)
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp),
                label = { Text("Contraseña", color = Color.White, fontSize = 22.sp, lineHeight = 28.sp) },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        Icon(imageVector = icon, contentDescription = "Mostrar/ocultar contraseña")
                    }
                },
                textObfuscationMode = if (isPasswordVisible) TextObfuscationMode.Visible else TextObfuscationMode.Hidden,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFFAE3251),
                    unfocusedContainerColor = Color(0xFFAE3251),
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically, // Alinea los elementos verticalmente
                horizontalArrangement = Arrangement.spacedBy(0.dp), // Elimina el espacio entre los elementos
                modifier = Modifier.padding(start = 0.dp, end = 0.dp, top = 4.dp) // Reduce el espacio entre los elementos, ajusta el top si es necesario
            ) {
                // Sección "¿No tienes cuenta?"
                Text(
                    text = "¿No tienes cuenta? ",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Crea una",
                    color = Color(0xFFFACDDD),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { navController.navigate("register_screen") }
                        .padding(end = 0.dp), // Asegura que no haya espacio a la derecha del texto
                    textAlign = TextAlign.Center
                )
            }

            // Botón de "Iniciar"
            Button(
                onClick = {
                    navController.navigate("second_screen")

                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(120.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAE3251)),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                )
            ) {
                Text(
                    text = "Iniciar",
                    fontSize = 22.sp, // Tamaño de la fuente
                    color = Color.White, // Color de texto
                    fontWeight = FontWeight.Bold // Negrita para mayor impacto
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFirstScreenLogin() {
    val navController = rememberNavController()
    FirstScreenLogin(navController = navController)
}


