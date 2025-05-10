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
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.UpdateUserRequest
import com.example.littleelephant.apiRest.UserViewModel
import com.example.littleelephant.data.TranslationManager
import com.example.littleelephant.data.dataStore
import com.example.littleelephant.naviagtion.UserSessionManager
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    navController: NavHostController? = null,
    viewModel: UserViewModel = viewModel()
) {
    // Contexto actual
    val context = LocalContext.current

    // Obtiene el email del usuario desde la sesión
    val email = remember { UserSessionManager.getEmail(context) }

    // Obtiene los datos del usuario
    val userData = viewModel.userData.value

    // Gradiente de fondo
    val beachSunsetBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFF6EFE4), Color(0xFFF5E6DB), Color(0xFFF1D8CE))
    )

    // Color de fondo para los inputs
    val blushColorArenaAtardecer = Color(0xFFF3D2B8)

    // Estado para el scroll
    val scrollState = rememberScrollState()

    // --- VARIABLES MUTABLES PARA LOS TEXTOS (TRADUCCIÓN) ---

    // Títulos y etiquetas
    var titleText by remember { mutableStateOf(TranslationManager.getString("preferences_title")) }
    var firstNameText by remember { mutableStateOf(TranslationManager.getString("first_name")) }
    var lastNameText by remember { mutableStateOf(TranslationManager.getString("last_name")) }
    var birthDateText by remember { mutableStateOf(TranslationManager.getString("birth_date")) }
    var genderText by remember { mutableStateOf(TranslationManager.getString("gender")) }
    var currentPasswordText by remember { mutableStateOf(TranslationManager.getString("current_password")) }
    var newPasswordText by remember { mutableStateOf(TranslationManager.getString("new_password")) }
    var saveChangesText by remember { mutableStateOf(TranslationManager.getString("save_changes")) }
    var deleteAccountText by remember { mutableStateOf(TranslationManager.getString("delete_account")) }

    // Mensajes de éxito y error
    var accountDeletedText by remember { mutableStateOf(TranslationManager.getString("account_deleted")) }
    var updateSuccessText by remember { mutableStateOf(TranslationManager.getString("update_success")) }
    var deleteAccountErrorText by remember { mutableStateOf(TranslationManager.getString("delete_account_error")) }
    var updateErrorText by remember { mutableStateOf(TranslationManager.getString("update_error")) }

    // Validaciones y mensajes de error
    var minFirstNameLengthText by remember { mutableStateOf(TranslationManager.getString("min_first_name_length")) }
    var minLastNameLengthText by remember { mutableStateOf(TranslationManager.getString("min_last_name_length")) }
    var invalidDateFormatText by remember { mutableStateOf(TranslationManager.getString("invalid_date_format")) }
    var invalidDateText by remember { mutableStateOf(TranslationManager.getString("invalid_date")) }
    var minAgeText by remember { mutableStateOf(TranslationManager.getString("min_age")) }
    var minPasswordLengthText by remember { mutableStateOf(TranslationManager.getString("min_password_length")) }
    var maxPasswordLengthText by remember { mutableStateOf(TranslationManager.getString("max_password_length")) }
    var mustContainUppercaseText by remember { mutableStateOf(TranslationManager.getString("must_contain_uppercase")) }
    var mustContainLowercaseText by remember { mutableStateOf(TranslationManager.getString("must_contain_lowercase")) }
    var mustContainNumberText by remember { mutableStateOf(TranslationManager.getString("must_contain_number")) }
    var mustContainSpecialCharText by remember { mutableStateOf(TranslationManager.getString("must_contain_special_char")) }
    var currentPasswordRequiredText by remember { mutableStateOf(TranslationManager.getString("current_password_required")) }
    var newPasswordRequiredText by remember { mutableStateOf(TranslationManager.getString("new_password_required")) }

    // Opciones de género
    var genderMaleText by remember { mutableStateOf(TranslationManager.getString("gender_male")) }
    var genderFemaleText by remember { mutableStateOf(TranslationManager.getString("gender_female")) }
    var genderOtherText by remember { mutableStateOf(TranslationManager.getString("gender_other")) }

    // Otros textos
    var yearsText by remember { mutableStateOf(TranslationManager.getString("years")) }
    var loadingText by remember { mutableStateOf(TranslationManager.getString("loading")) }

    /**
     * Función para actualizar los textos según el idioma seleccionado.
     */
    fun updateTexts() {
        titleText = TranslationManager.getString("preferences_title")
        firstNameText = TranslationManager.getString("first_name")
        lastNameText = TranslationManager.getString("last_name")
        birthDateText = TranslationManager.getString("birth_date")
        genderText = TranslationManager.getString("gender")
        currentPasswordText = TranslationManager.getString("current_password")
        newPasswordText = TranslationManager.getString("new_password")
        saveChangesText = TranslationManager.getString("save_changes")
        deleteAccountText = TranslationManager.getString("delete_account")

        accountDeletedText = TranslationManager.getString("account_deleted")
        updateSuccessText = TranslationManager.getString("update_success")
        deleteAccountErrorText = TranslationManager.getString("delete_account_error")
        updateErrorText = TranslationManager.getString("update_error")

        minFirstNameLengthText = TranslationManager.getString("min_first_name_length")
        minLastNameLengthText = TranslationManager.getString("min_last_name_length")
        invalidDateFormatText = TranslationManager.getString("invalid_date_format")
        invalidDateText = TranslationManager.getString("invalid_date")
        minAgeText = TranslationManager.getString("min_age")
        minPasswordLengthText = TranslationManager.getString("min_password_length")
        maxPasswordLengthText = TranslationManager.getString("max_password_length")
        mustContainUppercaseText = TranslationManager.getString("must_contain_uppercase")
        mustContainLowercaseText = TranslationManager.getString("must_contain_lowercase")
        mustContainNumberText = TranslationManager.getString("must_contain_number")
        mustContainSpecialCharText = TranslationManager.getString("must_contain_special_char")
        currentPasswordRequiredText = TranslationManager.getString("current_password_required")
        newPasswordRequiredText = TranslationManager.getString("new_password_required")

        genderMaleText = TranslationManager.getString("gender_male")
        genderFemaleText = TranslationManager.getString("gender_female")
        genderOtherText = TranslationManager.getString("gender_other")

        yearsText = TranslationManager.getString("years")
        loadingText = TranslationManager.getString("loading")
    }

    /**
     * Cargar el idioma inicial al entrar a la pantalla.
     * Obtiene el idioma seleccionado del DataStore. Si no existe, por defecto se establece en español ("es").
     * Luego, carga el archivo de traducción correspondiente y actualiza los textos.
     */
    LaunchedEffect(Unit) {
        val lang = context.dataStore.data.first()[stringPreferencesKey("language")] ?: "es"
        TranslationManager.loadLanguage(context, lang)
        updateTexts()
    }

    // --- ESTADOS PARA LOS CAMPOS DEL FORMULARIO ---

    // Estado para el nombre del usuario
    var firstName by remember { mutableStateOf("") }

    // Estado para el apellido del usuario
    var lastName by remember { mutableStateOf("") }

    // Estado para la fecha de nacimiento
    var birthDate by remember { mutableStateOf("") }

    // Estado para el género seleccionado
    var gender by remember { mutableStateOf("") }

    // Estado para la contraseña actual
    var currentPassword by remember { mutableStateOf("") }

    // Estado para la nueva contraseña
    var newPassword by remember { mutableStateOf("") }

    // --- OPCIONES DE GÉNERO ---

    // Lista de opciones de género a mostrar al usuario (traducidas)
    val genderOptionsDisplay = listOf(genderMaleText, genderFemaleText, genderOtherText)

    // Lista de valores para enviar al backend (en inglés)
    val genderOptionsBackend = listOf("male", "female", "other")

    // Estado para controlar si el dropdown de género está expandido o no
    var expanded by remember { mutableStateOf(false) }



    /**
     * Efecto para cargar los datos del usuario al obtener el email de la sesión.
     * Llama al método `fetchUserByEmailForProfile()` del ViewModel.
     */
    LaunchedEffect(email) {
        email?.let { viewModel.fetchUserByEmailForProfile(it) }
    }

    /**
     * Efecto para actualizar los estados del formulario cuando los datos del usuario cambian.
     */
    LaunchedEffect(userData) {
        userData?.let {
            firstName = it.firstName ?: ""
            lastName = it.lastName ?: ""
            birthDate = it.birthDate?.takeIf { it.length >= 10 }?.substring(0, 10) ?: ""
            gender = it.gender ?: ""
        }
    }

    /**
     * Función para validar los datos del formulario.
     * Retorna un mensaje de error si alguna validación falla, o null si todos los datos son válidos.
     */
    fun validarDatos(): String? {
        // Validar longitud del nombre
        if (firstName.length < 2) return minFirstNameLengthText

        // Validar longitud del apellido
        if (lastName.length < 2) return minLastNameLengthText

        // Validar formato de la fecha de nacimiento
        val fechaRegex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        if (!birthDate.matches(fechaRegex)) return invalidDateFormatText

        // Validar si la fecha es válida y cumple el requisito de edad mínima
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.isLenient = false
            val date = sdf.parse(birthDate) ?: return invalidDateText

            val calendar = Calendar.getInstance().apply { time = date }
            val today = Calendar.getInstance()
            val minAge = 5

            calendar.add(Calendar.YEAR, minAge)
            if (calendar.after(today)) {
                // Mensaje de error: "Debes tener al menos X años"
                return "$minAgeText $minAge $yearsText"
            }
        } catch (e: Exception) {
            return invalidDateText
        }

        // Validar combinación de contraseña actual y nueva contraseña
        if (currentPassword.isNotBlank() && newPassword.isBlank()) {
            return newPasswordRequiredText
        }

        if (newPassword.isNotBlank()) {
            // Verifica que se haya ingresado la contraseña actual
            if (currentPassword.isBlank()) {
                return currentPasswordRequiredText
            }
            if (newPassword.length < 6) return minPasswordLengthText
            if (newPassword.length > 20) return maxPasswordLengthText
            if (!newPassword.any { it.isUpperCase() }) return mustContainUppercaseText
            if (!newPassword.any { it.isLowerCase() }) return mustContainLowercaseText
            if (!newPassword.any { it.isDigit() }) return mustContainNumberText

            // Validar que contenga al menos un carácter especial
            val specialCharRegex = Regex("[^A-Za-z0-9]")
            if (!specialCharRegex.containsMatchIn(newPassword)) return mustContainSpecialCharText
        }

        // Si todas las validaciones pasan, retornar null
        return null
    }


    /**
     * Estructura principal del Scaffold.
     * Incluye una barra superior (TopAppBar) y un contenido principal.
     */
    Scaffold(
        // Barra superior
        topBar = {
            TopAppBar(
                // Título de la pantalla, traducido
                title = { Text(text = titleText, color = Color.White) },

                // Icono de navegación para retroceder
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },

                // Colores del TopAppBar
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF395173))
            )
        }
    ) { paddingValues ->

        // Comprobación: Si los datos del usuario están disponibles
        if (userData != null) {

            // Contenedor principal con Scroll y background
            Column(
                modifier = Modifier
                    .padding(paddingValues)  // Ajuste del padding para respetar la barra superior
                    .fillMaxSize()  // Ocupa todo el espacio disponible
                    .verticalScroll(scrollState)  // Habilita el scroll vertical
                    .background(brush = beachSunsetBrush)  // Fondo degradado
                    .padding(24.dp),  // Padding interno
                verticalArrangement = Arrangement.spacedBy(16.dp)  // Espacio entre elementos
            ) {

                // Imagen de perfil (Ejemplo visual)
                Image(
                    painter = painterResource(id = R.drawable.elefantepiscina),
                    contentDescription = "Perfil de usuario",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Fit  // Ajuste del contenido de la imagen
                )

                // Campo de texto para el nombre
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text(firstNameText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(blushColorArenaAtardecer)  // Fondo del campo
                )

                // Campo de texto para el apellido
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text(lastNameText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(blushColorArenaAtardecer)
                )

                // Campo de texto para la fecha de nacimiento
                OutlinedTextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    label = { Text(birthDateText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(blushColorArenaAtardecer),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)  // Solo números permitidos
                )

                /**
                 * Menú desplegable para seleccionar el género.
                 * Los valores visibles son los traducidos (genderOptionsDisplay),
                 * pero el valor enviado al backend se mantiene en inglés (genderOptionsBackend).
                 */
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }  // Alterna entre abierto y cerrado
                ) {

                    // Campo de texto que actúa como disparador del menú
                    OutlinedTextField(
                        readOnly = true,  // Campo no editable, solo seleccionable
                        value = when (gender) {
                            "male" -> genderMaleText
                            "female" -> genderFemaleText
                            "other" -> genderOtherText
                            else -> ""
                        },
                        onValueChange = {},
                        label = { Text(genderText) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },  // Icono desplegable
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .background(blushColorArenaAtardecer)
                    )

                    // Menú desplegable de opciones de género
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },  // Cierra el menú al hacer clic fuera
                        modifier = Modifier.background(blushColorArenaAtardecer)
                    ) {
                        genderOptionsDisplay.forEachIndexed { index, displayText ->
                            DropdownMenuItem(
                                text = { Text(displayText) },
                                onClick = {
                                    // Asigna el valor seleccionado en inglés para el backend
                                    gender = genderOptionsBackend[index]
                                    expanded = false  // Cierra el menú
                                }
                            )
                        }
                    }
                }

                // Campo de texto para la contraseña actual
                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = { Text(currentPasswordText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(blushColorArenaAtardecer),
                    visualTransformation = PasswordVisualTransformation()  // Oculta la contraseña
                )

                // Campo de texto para la nueva contraseña
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text(newPasswordText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(blushColorArenaAtardecer),
                    visualTransformation = PasswordVisualTransformation()
                )

                // Espacio entre elementos
                Spacer(modifier = Modifier.height(24.dp))

                /**
                 * Botón para guardar los cambios del perfil del usuario.
                 * Realiza la validación de los datos antes de enviar la solicitud al backend.
                 */
                Button(
                    onClick = {
                        // Llama a la función de validación de datos
                        val error = validarDatos()

                        // Si hay un error, muestra el mensaje correspondiente
                        if (error != null) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        // Si el email está disponible, procede a crear la solicitud de actualización
                        if (email != null) {
                            val request = UpdateUserRequest(
                                firstName = firstName,
                                lastName = lastName,
                                birthDate = birthDate,
                                gender = gender,
                                password = if (newPassword.isNotBlank()) newPassword else null,
                                currentPassword = if (newPassword.isNotBlank()) currentPassword else null
                            )

                            // Envía la solicitud de actualización al backend
                            viewModel.updateUserProfile(
                                email = email,
                                request = request,

                                // Acción al completarse la solicitud con éxito
                                onSuccess = {
                                    Toast.makeText(context, saveChangesText, Toast.LENGTH_SHORT).show()
                                },

                                // Acción si ocurre un error en la actualización
                                onError = {
                                    Toast.makeText(context, "$updateErrorText $it", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },
                    // Estilo del botón de guardar cambios
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE5A173),  // Color de fondo
                        contentColor = Color.Black  // Color del texto
                    ),
                    shape = RoundedCornerShape(16.dp),  // Bordes redondeados
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)  // Elevación del botón
                ) {
                    // Texto del botón, traducido
                    Text(saveChangesText)
                }

                /**
                 * Botón para eliminar la cuenta del usuario.
                 * Realiza la solicitud al backend y gestiona el estado de sesión.
                 */
                Button(
                    onClick = {
                        // Verifica que el email esté disponible antes de proceder
                        if (email != null) {
                            viewModel.deleteUser(
                                email = email,

                                // Acción al completarse la eliminación con éxito
                                onSuccess = {
                                    Toast.makeText(context, accountDeletedText, Toast.LENGTH_SHORT).show()

                                    // Limpia la sesión y navega a la pantalla de login
                                    UserSessionManager.clearSession(context)
                                    navController?.navigate("login_screen") {
                                        popUpTo("preferences_screen") { inclusive = true }
                                    }
                                },

                                // Acción si ocurre un error durante la eliminación
                                onError = {
                                    Toast.makeText(context, "$deleteAccountErrorText $it", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },
                    // Estilo del botón de eliminar cuenta
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD9534F),  // Color de fondo (rojo)
                        contentColor = Color.White  // Color del texto
                    ),
                    shape = RoundedCornerShape(16.dp),  // Bordes redondeados
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)  // Elevación del botón
                ) {
                    // Texto del botón, traducido
                    Text(deleteAccountText)
                }
            }
        }
        // Estado de carga si los datos del usuario no están disponibles
        else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Indicador de progreso circular
                    CircularProgressIndicator()

                    // Espacio entre el indicador y el texto
                    Spacer(modifier = Modifier.height(16.dp))

                    // Texto de carga, traducido
                    Text(text = loadingText)
                }
            }
        }
    }
}








