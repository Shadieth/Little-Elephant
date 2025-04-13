package com.example.littleelephant.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.RegisterRequest
import com.example.littleelephant.apiRest.UserViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush


// Colores reutilizables
val BackgroundColor = Color(0xFFFACDDD)
val TopBarColor = Color(0xFF395173)
val ButtonColor = Color(0xFFAE3251)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController? = null) {

    // Obtener la instancia de UserViewModel
    val userViewModel: UserViewModel = viewModel()

    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Observar los resultados de LiveData desde el ViewModel
    val registrationSuccess by userViewModel.registrationSuccess.observeAsState()
    val registrationError by userViewModel.registrationError.observeAsState()

    var registrationSuccessBoolean by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFF475A75), // Turquesa neón
                                TopBarColor // Color definido para la barra superior
                            )
                        )
                    )
            ) {
                TopAppBar(
                    title = { Text("Crear Cuenta", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController?.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Regresar",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent // Lo hacemos transparente para que se vea el gradiente
                    )
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            BackgroundColor, // Color base
                            Color(0xFFFADAE5)
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                // Imagen principal
                val image: Painter = painterResource(id = R.drawable.elefantecumpleanero)
                Image(
                    painter = image,
                    contentDescription = "Imagen de bienvenida",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Campos de texto
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Nombre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF222831), Color(0xFF393E46)) // Degradado oscuro futurista
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes más redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto en turquesa neón cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta en neón cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo más oscuro sin foco
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior cuando no está enfocado
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Apellido") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF222831), Color(0xFF393E46)) // Degradado oscuro futurista
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes más redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto en turquesa neón cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta en neón cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo más oscuro sin foco
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior cuando no está enfocado
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    label = { Text("Fecha de Nacimiento (AAAA-MM-DD)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF222831), Color(0xFF393E46)) // Degradado oscuro futurista
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes más redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto en turquesa neón cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta en neón cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo más oscuro sin foco
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior cuando no está enfocado
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                var expanded by remember { mutableStateOf(false) }
                val genderOptions = listOf("male", "female", "other")

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(Color(0xFF222831), Color(0xFF393E46))
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, enabled = true)
                    ) {
                        TextField(
                            value = selectedGender,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Género") },
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Expandir",
                                    tint = Color.White
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF00FFF5),
                                unfocusedTextColor = Color(0xFFEEEEEE),
                                cursorColor = Color(0xFF00FFF5),
                                focusedLabelColor = Color(0xFF00FFF5),
                                unfocusedLabelColor = Color(0xFFEEEEEE),
                                focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f),
                                unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(Color(0xFFC5687D), Color(0xFFCE6F83)) // Degradado oscuro futurista
                                )
                            )
                    ) {
                        genderOptions.forEach { gender ->
                            DropdownMenuItem(
                                text = { Text(gender, color = Color.White) },
                                onClick = {
                                    selectedGender = gender
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Electrónico") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF222831), Color(0xFF393E46)) // Degradado oscuro futurista
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes más redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto en turquesa neón cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta en neón cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo más oscuro sin foco
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior cuando no está enfocado
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF222831), Color(0xFF393E46)) // Degradado oscuro futurista
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes más redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto en turquesa neón cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta en neón cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo más oscuro sin foco
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior cuando no está enfocado
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF222831), Color(0xFF393E46)) // Degradado oscuro futurista
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes más redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto en turquesa neón cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta en neón cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo más oscuro sin foco
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior cuando no está enfocado
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón para crear cuenta
                Button(
                    onClick = {
                        if (firstName.isBlank() || lastName.isBlank() || birthDate.isBlank() ||
                            selectedGender.isBlank() || email.isBlank() || password.isBlank() ||
                            confirmPassword.isBlank() || password != confirmPassword
                        ) {
                            Toast.makeText(context, "Por favor, complete todos los campos correctamente.", Toast.LENGTH_SHORT).show()
                        } else {
                            val request = RegisterRequest(
                                firstName = firstName,
                                lastName = lastName,
                                birthDate = birthDate,
                                gender = selectedGender,
                                email = email,
                                password = password
                            )
                            userViewModel.registerUser(request)

                            registrationSuccessBoolean = true
                        }
                        if (registrationSuccessBoolean) {
                            // Navegar a la pantalla de éxito del registro y eliminar la pantalla de registro del stack
                            navController?.navigate("registration_success") {
                                // Elimina la pantalla de registro para que el usuario no pueda volver atrás
                                popUpTo("register") { inclusive = true }
                                // Evita que se agregue la pantalla de éxito al stack de navegación
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFFAD4962), // Turquesa neón
                                    ButtonColor
                                )
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes redondeados
                        )
                        .shadow(
                            elevation = 8.dp, // Sombra con ligera elevación
                            shape = RoundedCornerShape(16.dp), // Aseguramos que la sombra sigue el mismo radio de borde
                            ambientColor = Color.Black.copy(alpha = 0.2f), // Color oscuro y leve
                            spotColor = Color.Black.copy(alpha = 0.1f) // Sombra más suave en el punto de contacto
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent // Fondo transparente para dejar ver el Brush
                    )
                ) {
                    Text("Crear Cuenta", color = Color.White) // Texto blanco para resaltar sobre el degradado
                }


                // Mostrar los mensajes de éxito o error
                registrationSuccess?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }

                registrationError?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateAccount() {
    RegisterScreen()
}

