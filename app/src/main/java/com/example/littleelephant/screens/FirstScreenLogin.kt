package com.example.littleelephant.screens

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.LoginRequest
import com.example.littleelephant.apiRest.UserViewModel
import com.example.littleelephant.ui.theme.LittleElephantTheme

@Composable
fun FirstScreenLogin(navController: NavController, viewModel: UserViewModel = viewModel()) {
    Scaffold {
        LittleElephantTheme {
            Content(modifier = Modifier.padding(it).background(color = Color(0xFFFACDDD)), navController, viewModel)
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier, navController: NavController, viewModel: UserViewModel) {
    Column(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.elefantelenguaafuera),
            contentDescription = "Imagen superior",
            modifier = Modifier
                .fillMaxWidth().weight(1f),
            contentScale = ContentScale.Crop
        )
        BottomContainer(modifier = Modifier.fillMaxSize().weight(1f), navController, viewModel)
    }
}

@Composable
fun BottomContainer(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: UserViewModel
) {
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
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            val usuario = remember { mutableStateOf(TextFieldValue()) }

            BasicTextField(
                value = usuario.value,
                onValueChange = { usuario.value = it },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
                    .background(Color(0xFFAE3251), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp,
                    lineHeight = 28.sp
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.padding(start = 0.dp, end = 0.dp, top = 4.dp)
            ) {
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
                        .padding(end = 0.dp),
                    textAlign = TextAlign.Center
                )
            }

            Button(
                onClick = {
                    val loginRequest = LoginRequest(
                        email = usuario.value.text,
                        password = passwordState.text.toString()
                    )
                    viewModel.loginUser(loginRequest)  // Llamada al ViewModel para el login
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
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Observando el resultado de login
            val context = LocalContext.current
            val loginSuccess by viewModel.loginSuccess.observeAsState()
            val loginError by viewModel.loginError.observeAsState()

            // Manejo de la respuesta del login
            LaunchedEffect(loginSuccess) {
                loginSuccess?.let {
                    // Muestra un mensaje de éxito y navega a la pantalla de inicio
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    navController.navigate("second_screen") // Asegúrate de que esta ruta esté correctamente configurada
                    viewModel.clearLoginState() // Limpia el estado para evitar ejecuciones repetidas
                }
            }

            LaunchedEffect(loginError) {
                loginError?.let {
                    // Muestra un mensaje de error
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearLoginState() // Limpia el estado después de mostrar el error
                }
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


