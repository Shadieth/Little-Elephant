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
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.LoginRequest
import com.example.littleelephant.apiRest.UserViewModel
import com.example.littleelephant.data.TranslationManager
import com.example.littleelephant.data.dataStore
import com.example.littleelephant.naviagtion.UserSessionManager
import com.example.littleelephant.ui.theme.LittleElephantTheme
import kotlinx.coroutines.flow.first

@Composable
fun LoginScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val context = LocalContext.current

    // Definición de textos dinámicos
    var appNameText by remember { mutableStateOf(TranslationManager.getString("app_name")) }
    var usernameText by remember { mutableStateOf(TranslationManager.getString("username")) }
    var passwordText by remember { mutableStateOf(TranslationManager.getString("password")) }
    var noAccountText by remember { mutableStateOf(TranslationManager.getString("no_account")) }
    var createOneText by remember { mutableStateOf(TranslationManager.getString("create_one")) }
    var loginButtonText by remember { mutableStateOf(TranslationManager.getString("login_button")) }
    var fillAllFieldsText by remember { mutableStateOf(TranslationManager.getString("fill_all_fields")) }
    var validEmailText by remember { mutableStateOf(TranslationManager.getString("valid_email")) }
    var passwordMinLengthText by remember { mutableStateOf(TranslationManager.getString("password_min_length")) }
    var loginErrorText by remember { mutableStateOf(TranslationManager.getString("login_error")) }

    /**
     * Función para actualizar los textos al cambiar el idioma.
     */
    fun updateTexts() {
        appNameText = TranslationManager.getString("app_name")
        usernameText = TranslationManager.getString("username")
        passwordText = TranslationManager.getString("password")
        noAccountText = TranslationManager.getString("no_account")
        createOneText = TranslationManager.getString("create_one")
        loginButtonText = TranslationManager.getString("login_button")
        fillAllFieldsText = TranslationManager.getString("fill_all_fields")
        validEmailText = TranslationManager.getString("valid_email")
        passwordMinLengthText = TranslationManager.getString("password_min_length")
        loginErrorText = TranslationManager.getString("login_error")
    }

    /**
     * Carga inicial del idioma al acceder a la pantalla.
     */
    LaunchedEffect(Unit) {
        val lang = context.dataStore.data.first()[stringPreferencesKey("language")] ?: "es"
        TranslationManager.loadLanguage(context, lang)
        updateTexts()
    }

    // Estructura de la pantalla utilizando un Scaffold
    Scaffold {
        LittleElephantTheme {
            Content(
                modifier = Modifier.padding(it).background(color = Color(0xFFFACDDD)),
                navController = navController,
                viewModel = viewModel,
                appNameText = appNameText,
                usernameText = usernameText,
                passwordText = passwordText,
                noAccountText = noAccountText,
                createOneText = createOneText,
                loginButtonText = loginButtonText,
                fillAllFieldsText = fillAllFieldsText,
                validEmailText = validEmailText,
                passwordMinLengthText = passwordMinLengthText,
                loginErrorText = loginErrorText
            )
        }
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: UserViewModel,
    appNameText: String,
    usernameText: String,
    passwordText: String,
    noAccountText: String,
    createOneText: String,
    loginButtonText: String,
    fillAllFieldsText: String,
    validEmailText: String,
    passwordMinLengthText: String,
    loginErrorText: String
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Imagen superior
        Image(
            painter = painterResource(id = R.drawable.elefantelenguaafuera),
            contentDescription = "Imagen superior",
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentScale = ContentScale.Crop
        )

        // Contenedor inferior que contiene los campos de entrada y botón
        BottomContainer(
            modifier = Modifier.fillMaxSize().weight(1f),
            navController = navController,
            viewModel = viewModel,
            appNameText = appNameText,
            usernameText = usernameText,
            passwordText = passwordText,
            noAccountText = noAccountText,
            createOneText = createOneText,
            loginButtonText = loginButtonText,
            fillAllFieldsText = fillAllFieldsText,
            validEmailText = validEmailText,
            passwordMinLengthText = passwordMinLengthText,
            loginErrorText = loginErrorText
        )
    }
}

@Composable
fun BottomContainer(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: UserViewModel,
    appNameText: String,
    usernameText: String,
    passwordText: String,
    noAccountText: String,
    createOneText: String,
    loginButtonText: String,
    fillAllFieldsText: String,
    validEmailText: String,
    passwordMinLengthText: String,
    loginErrorText: String
) {
    // Contenedor principal con fondo azul oscuro y alineación centrada
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF395173)),
        contentAlignment = Alignment.Center
    ) {
        // Columna principal que organiza los elementos verticalmente
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),  // Margen interno
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Título de la aplicación
            Text(
                text = appNameText,  // Texto pasado como parámetro
                color = Color(0xFFFACDDD),  // Color rosado
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),  // Espaciado inferior
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 42.sp,  // Tamaño del texto
                    fontWeight = FontWeight.Bold  // Texto en negrita
                )
            )

            // Estado para el campo de entrada del usuario
            val usuario = remember { mutableStateOf(TextFieldValue()) }

            // Estado para el campo de entrada de la contraseña
            var passwordState by remember { mutableStateOf(TextFieldState("")) }

            // Estado para mostrar/ocultar la contraseña
            var isPasswordVisible by remember { mutableStateOf(false) }

            // Contexto actual para mostrar Toasts
            val context = LocalContext.current

            // Campo de entrada del usuario
            BasicTextField(
                value = usuario.value,
                onValueChange = { usuario.value = it },
                modifier = Modifier
                    .height(80.dp)  // Altura del campo
                    .fillMaxWidth(0.8f)  // Anchura proporcional
                    .padding(vertical = 8.dp)
                    .background(Color(0xFFAE3251), shape = RoundedCornerShape(12.dp))  // Fondo rojo oscuro con bordes redondeados
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
                        // Texto placeholder si el campo está vacío
                        if (usuario.value.text.isEmpty()) {
                            Text(
                                text = usernameText,  // Texto pasado como parámetro
                                style = TextStyle(color = Color.White, fontSize = 22.sp)
                            )
                        }
                        innerTextField()
                    }
                },
                singleLine = true,
                cursorBrush = SolidColor(Color.White)
            )

            // Campo de entrada de contraseña con ícono para mostrar/ocultar
            OutlinedSecureTextField(
                state = passwordState,
                modifier = Modifier
                    .height(85.dp)
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp),
                label = {
                    Text(
                        text = passwordText,  // Texto pasado como parámetro
                        color = Color.White,
                        fontSize = 22.sp,
                        lineHeight = 28.sp
                    )
                },
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

            // Línea de "No tienes cuenta" y enlace para crear cuenta
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = noAccountText,  // Texto pasado como parámetro
                    color = Color.White,
                    fontSize = 18.sp,
                )
                Text(
                    text = createOneText,  // Texto pasado como parámetro
                    color = Color(0xFFFACDDD),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("register_screen")
                    }
                )
            }

            // Botón de Login
            Button(
                onClick = {
                    val email = usuario.value.text.trim()
                    val password = passwordState.text.toString().trim()

                    when {
                        // Validación de campos vacíos
                        email.isEmpty() || password.isEmpty() -> {
                            Toast.makeText(context, fillAllFieldsText, Toast.LENGTH_SHORT).show()
                        }

                        // Validación de email
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                            Toast.makeText(context, validEmailText, Toast.LENGTH_SHORT).show()
                        }

                        // Validación de longitud de contraseña
                        password.length < 6 -> {
                            Toast.makeText(context, passwordMinLengthText, Toast.LENGTH_SHORT).show()
                        }

                        // Si pasa todas las validaciones, realizar el login
                        else -> {
                            val loginRequest = LoginRequest(email = email, password = password)
                            viewModel.loginUser(loginRequest)
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(120.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAE3251)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp, pressedElevation = 4.dp)
            ) {
                Text(
                    text = loginButtonText,  // Texto pasado como parámetro
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Observador del estado de login
            val loginSuccess by viewModel.loginSuccess.observeAsState()
            val loginError by viewModel.loginError.observeAsState()

            // Manejo del éxito en el login
            LaunchedEffect(loginSuccess) {
                loginSuccess?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    UserSessionManager.saveEmail(context, usuario.value.text)
                    navController.navigate("ecosystems_screen")
                    viewModel.clearLoginState()
                }
            }

            // Manejo del error en el login
            LaunchedEffect(loginError) {
                loginError?.let {
                    Toast.makeText(context, loginErrorText, Toast.LENGTH_SHORT).show()
                    viewModel.clearLoginState()
                }
            }
        }
    }
}
/**
 * Vista previa de la pantalla de login.
 * Esta función permite visualizar el diseño de la pantalla de login
 * en el editor de Android Studio sin necesidad de ejecutar la aplicación.
 */
@Preview(showBackground = true)  // Muestra el fondo de la pantalla en la vista previa
@Composable
fun LoginScreenPreview() {

    // Crea un controlador de navegación simulado para la vista previa
    val navController = rememberNavController()

    // Llama a la función LoginScreen, pasándole el controlador de navegación
    LoginScreen(navController = navController)
}



