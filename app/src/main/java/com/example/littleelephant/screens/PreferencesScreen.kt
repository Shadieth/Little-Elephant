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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.UpdateUserRequest
import com.example.littleelephant.apiRest.UserViewModel
import com.example.littleelephant.naviagtion.UserSessionManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    navController: NavHostController? = null,
    viewModel: UserViewModel = viewModel()
) {
    val context = LocalContext.current
    val email = remember { UserSessionManager.getEmail(context) }
    val userData = viewModel.userData.value

    val beachSunsetBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFF6EFE4), Color(0xFFF5E6DB), Color(0xFFF1D8CE))
    )
    val blushColorArenaAtardecer = Color(0xFFF3D2B8)
    val scrollState = rememberScrollState()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    val genderOptions = listOf("male", "female", "other")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(email) {
        email?.let { viewModel.fetchUserByEmailForProfile(it) }
    }

    LaunchedEffect(userData) {
        userData?.let {
            firstName = it.firstName ?: ""
            lastName = it.lastName ?: ""
            birthDate = it.birthDate?.takeIf { it.length >= 10 }?.substring(0, 10) ?: ""
            gender = it.gender ?: ""
        }
    }

    fun validarDatos(): String? {
        if (firstName.length < 2) return "El nombre debe tener al menos 2 caracteres"
        if (lastName.length < 2) return "El apellido debe tener al menos 2 caracteres"
        val fechaRegex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        if (!birthDate.matches(fechaRegex)) return "La fecha debe tener el formato AAAA-MM-DD"

        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.isLenient = false
            val date = sdf.parse(birthDate) ?: return "Fecha inválida"

            val calendar = Calendar.getInstance().apply { time = date }
            val today = Calendar.getInstance()
            val minAge = 5

            calendar.add(Calendar.YEAR, minAge)
            if (calendar.after(today)) {
                return "Debes tener al menos $minAge años"
            }
        } catch (e: Exception) {
            return "Fecha inválida"
        }

        if (newPassword.isNotBlank()) {
            if (newPassword.length < 6) return "La contraseña debe tener al menos 6 caracteres"
            if (newPassword.length > 20) return "La contraseña no debe superar los 20 caracteres"
            if (!newPassword.any { it.isUpperCase() }) return "La contraseña debe contener una mayúscula"
            if (!newPassword.any { it.isLowerCase() }) return "La contraseña debe contener una minúscula"
            if (!newPassword.any { it.isDigit() }) return "La contraseña debe contener un número"
            val specialCharRegex = Regex("[^A-Za-z0-9]")
            if (!specialCharRegex.containsMatchIn(newPassword)) return "La contraseña debe incluir un carácter especial"
        }

        return null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preferencias", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF395173))
            )
        }
    ) { paddingValues ->
        if (userData != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(brush = beachSunsetBrush)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.elefantepiscina),
                    contentDescription = "Perfil de usuario",
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentScale = ContentScale.Fit
                )

                OutlinedTextField(firstName, { firstName = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth().background(blushColorArenaAtardecer))
                OutlinedTextField(lastName, { lastName = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth().background(blushColorArenaAtardecer))
                OutlinedTextField(birthDate, { birthDate = it }, label = { Text("Fecha de nacimiento") },
                    modifier = Modifier.fillMaxWidth().background(blushColorArenaAtardecer), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                    OutlinedTextField(
                        readOnly = true, value = gender, onValueChange = {},
                        label = { Text("Género") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth().background(blushColorArenaAtardecer)
                    )
                    ExposedDropdownMenu(expanded, onDismissRequest = { expanded = false },
                        modifier = Modifier.background(blushColorArenaAtardecer)) {
                        genderOptions.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                gender = it
                                expanded = false
                            })
                        }
                    }
                }

                OutlinedTextField(currentPassword, { currentPassword = it }, label = { Text("Contraseña actual") },
                    modifier = Modifier.fillMaxWidth().background(blushColorArenaAtardecer), visualTransformation = PasswordVisualTransformation())

                OutlinedTextField(newPassword, { newPassword = it }, label = { Text("Nueva contraseña") },
                    modifier = Modifier.fillMaxWidth().background(blushColorArenaAtardecer), visualTransformation = PasswordVisualTransformation())

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val error = validarDatos()
                        if (error != null) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if (email != null) {
                            val request = UpdateUserRequest(
                                firstName = firstName,
                                lastName = lastName,
                                birthDate = birthDate,
                                gender = gender,
                                password = if (newPassword.isNotBlank()) newPassword else null,
                                currentPassword = if (newPassword.isNotBlank()) currentPassword else null
                            )
                            viewModel.updateUserProfile(
                                email = email,
                                request = request,
                                onSuccess = {
                                    Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                                },
                                onError = {
                                    Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE5A173),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text("Guardar cambios")
                }

                Button(
                    onClick = {
                        if (email != null) {
                            viewModel.deleteUser(
                                email = email,
                                onSuccess = {
                                    Toast.makeText(context, "Cuenta eliminada", Toast.LENGTH_SHORT).show()
                                    UserSessionManager.clearSession(context)
                                    navController?.navigate("login_screen") {
                                        popUpTo("preferences_screen") { inclusive = true }
                                    }
                                },
                                onError = {
                                    Toast.makeText(context, "Error al eliminar la cuenta: $it", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD9534F),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text("Eliminar cuenta")
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}









