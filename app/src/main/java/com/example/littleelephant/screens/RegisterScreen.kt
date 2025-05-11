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
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.littleelephant.data.TranslationManager
import com.example.littleelephant.data.dataStore
import kotlinx.coroutines.flow.first

// Colores reutilizables
val BackgroundColor = Color(0xFFFACDDD)  // Fondo principal, tono pastel rosado
val TopBarColor = Color(0xFF395173)      // Color del TopBar, azul oscuro
val ButtonColor = Color(0xFFAE3251)      // Color para los botones, tono rojizo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController? = null) {

    // Obtener la instancia de UserViewModel
    val userViewModel: UserViewModel = viewModel()

    val context = LocalContext.current

    // --- VARIABLES DE TEXTO (TRADUCCIÓN) ---
    /**
     * Variables mutables para los textos traducidos que se actualizan dinámicamente
     * según el idioma seleccionado.
     */
    var createAccountText by remember { mutableStateOf(TranslationManager.getString("create_account")) }
    var firstNameText by remember { mutableStateOf(TranslationManager.getString("first_name")) }
    var lastNameText by remember { mutableStateOf(TranslationManager.getString("last_name")) }
    var birthDateText by remember { mutableStateOf(TranslationManager.getString("birth_date")) }
    var genderText by remember { mutableStateOf(TranslationManager.getString("gender")) }
    var emailText by remember { mutableStateOf(TranslationManager.getString("email")) }
    var passwordText by remember { mutableStateOf(TranslationManager.getString("password")) }
    var confirmPasswordText by remember { mutableStateOf(TranslationManager.getString("confirm_password")) }
    var fieldsRequiredText by remember { mutableStateOf(TranslationManager.getString("fields_required")) }
    var invalidDateFormatText by remember { mutableStateOf(TranslationManager.getString("invalid_date_format")) }
    var invalidMonthText by remember { mutableStateOf(TranslationManager.getString("invalid_month")) }
    var invalidDayText by remember { mutableStateOf(TranslationManager.getString("invalid_day")) }
    var invalidYearText by remember { mutableStateOf(TranslationManager.getString("invalid_year")) }
    var invalidDateText by remember { mutableStateOf(TranslationManager.getString("invalid_date")) }
    var passwordsMismatchText by remember { mutableStateOf(TranslationManager.getString("passwords_mismatch")) }
    var invalidEmailText by remember { mutableStateOf(TranslationManager.getString("invalid_email")) }
    var passwordMinLengthText by remember { mutableStateOf(TranslationManager.getString("password_min_length")) }
    var passwordUppercaseText by remember { mutableStateOf(TranslationManager.getString("password_uppercase")) }
    var passwordLowercaseText by remember { mutableStateOf(TranslationManager.getString("password_lowercase")) }
    var passwordDigitText by remember { mutableStateOf(TranslationManager.getString("password_digit")) }
    var passwordSpecialCharText by remember { mutableStateOf(TranslationManager.getString("password_special_char")) }
    var maleText by remember { mutableStateOf(TranslationManager.getString("male")) }
    var femaleText by remember { mutableStateOf(TranslationManager.getString("female")) }
    var otherText by remember { mutableStateOf(TranslationManager.getString("other")) }
    var firstNameMinLengthText by remember { mutableStateOf(TranslationManager.getString("first_name_min_length")) }
    var lastNameMinLengthText by remember { mutableStateOf(TranslationManager.getString("last_name_min_length")) }
    var userExistsText by remember { mutableStateOf(TranslationManager.getString("user_exists")) }


    /**
     * Función para actualizar los textos según el idioma seleccionado.
     */
    fun updateTexts() {
        createAccountText = TranslationManager.getString("create_account")
        firstNameText = TranslationManager.getString("first_name")
        lastNameText = TranslationManager.getString("last_name")
        birthDateText = TranslationManager.getString("birth_date")
        genderText = TranslationManager.getString("gender")
        emailText = TranslationManager.getString("email")
        passwordText = TranslationManager.getString("password")
        confirmPasswordText = TranslationManager.getString("confirm_password")
        fieldsRequiredText = TranslationManager.getString("fields_required")
        invalidDateFormatText = TranslationManager.getString("invalid_date_format")
        invalidMonthText = TranslationManager.getString("invalid_month")
        invalidDayText = TranslationManager.getString("invalid_day")
        invalidYearText = TranslationManager.getString("invalid_year")
        invalidDateText = TranslationManager.getString("invalid_date")
        passwordsMismatchText = TranslationManager.getString("passwords_mismatch")
        invalidEmailText = TranslationManager.getString("invalid_email")
        passwordMinLengthText = TranslationManager.getString("password_min_length")
        passwordUppercaseText = TranslationManager.getString("password_uppercase")
        passwordLowercaseText = TranslationManager.getString("password_lowercase")
        passwordDigitText = TranslationManager.getString("password_digit")
        passwordSpecialCharText = TranslationManager.getString("password_special_char")
        maleText = TranslationManager.getString("male")
        femaleText = TranslationManager.getString("female")
        otherText = TranslationManager.getString("other")
        firstNameMinLengthText = TranslationManager.getString("first_name_min_length")
        lastNameMinLengthText = TranslationManager.getString("last_name_min_length")
        userExistsText = TranslationManager.getString("user_exists")
    }

    /**
     * Cargar el idioma inicial al entrar a la pantalla.
     */
    LaunchedEffect(Unit) {
        val lang = context.dataStore.data.first()[stringPreferencesKey("language")] ?: "es"
        TranslationManager.loadLanguage(context, lang)
        updateTexts()
    }

    // --- ESTADOS PARA LOS CAMPOS DE FORMULARIO ---
    /**
     * Estados para los campos del formulario de registro.
     */
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    /**
     * Función para validar los campos del formulario de registro.
     * Retorna un mensaje de error si alguna validación falla, o null si todos los datos son válidos.
     */
    fun validarRegistro(
        firstName: String,
        lastName: String,
        birthDate: String,
        gender: String,
        email: String,
        password: String,
        confirmPassword: String,
        existingUsers: List<String>
    ): String? {

        /**
         * Verificar si todos los campos están completos.
         */
        if (firstName.isBlank() || lastName.isBlank() || birthDate.isBlank() ||
            gender.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()
        ) {
            return fieldsRequiredText
        }

        /**
         * Verificar longitud mínima del nombre y apellido (al menos 2 caracteres).
         */
        if (firstName.length < 2) {
            return firstNameMinLengthText
        }

        if (lastName.length < 2) {
            return lastNameMinLengthText
        }

        /**
         * Validar formato de la fecha de nacimiento (AAAA-MM-DD).
         */
        val fechaRegex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        if (!birthDate.matches(fechaRegex)) {
            return invalidDateFormatText
        }

        /**
         * Validar que la fecha ingresada sea válida.
         */
        try {
            val partes = birthDate.split("-")
            val year = partes[0].toInt()
            val month = partes[1].toInt()
            val day = partes[2].toInt()

            if (month !in 1..12) return invalidMonthText
            if (day !in 1..31) return invalidDayText
            if (year !in 1900..2100) return invalidYearText

        } catch (e: Exception) {
            return invalidDateText
        }

        /**
         * Verificar si las contraseñas coinciden.
         */
        if (password != confirmPassword) {
            return passwordsMismatchText
        }

        /**
         * Validar formato del correo electrónico.
         */
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        if (!email.matches(emailRegex)) {
            return invalidEmailText
        }

        /**
         * Verificar si el usuario ya existe.
         */
        if (existingUsers.contains(email)) {
            return userExistsText
        }

        /**
         * Verificar longitud mínima de la contraseña.
         */
        if (password.length < 6) {
            return passwordMinLengthText
        }

        /**
         * Verificar si la contraseña contiene al menos una letra mayúscula.
         */
        if (!password.any { it.isUpperCase() }) {
            return passwordUppercaseText
        }

        /**
         * Verificar si la contraseña contiene al menos una letra minúscula.
         */
        if (!password.any { it.isLowerCase() }) {
            return passwordLowercaseText
        }

        /**
         * Verificar si la contraseña contiene al menos un dígito numérico.
         */
        if (!password.any { it.isDigit() }) {
            return passwordDigitText
        }

        /**
         * Verificar si la contraseña contiene al menos un carácter especial.
         */
        val specialCharRegex = Regex("[^A-Za-z0-9]")
        if (!specialCharRegex.containsMatchIn(password)) {
            return passwordSpecialCharText
        }

        /**
         * Si todas las validaciones pasan, retornar null (Todo correcto).
         */
        return null
    }

    /**
     * Scaffold: Estructura principal de la pantalla.
     * Contiene la barra superior (TopBar), el contenido principal y los paddings internos.
     */
    Scaffold(
        topBar = {
            /**
             * TopBar: Barra superior de la pantalla.
             * Aplica un gradiente horizontal y un título dinámico.
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFF475A75), // Turquesa neón
                                TopBarColor // Color predefinido para la barra superior
                            )
                        )
                    )
            ) {
                TopAppBar(
                    title = {
                        Text(
                            createAccountText, // Texto dinámico para el título
                            color = Color.White
                        )
                    },
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
                        containerColor = Color.Transparent // Transparente para mostrar el gradiente de fondo
                    )
                )
            }
        },
    ) { paddingValues ->

        /**
         * Contenedor principal: Fondo con gradiente vertical y padding aplicados.
         */
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            BackgroundColor, // Color base del fondo
                            Color(0xFFFADAE5) // Gradiente hasta un tono más claro
                        )
                    )
                )
                .padding(paddingValues)
        ) {

            /**
             * Column: Estructura vertical que contiene los elementos de la pantalla.
             * Incluye el campo de imagen, campos de texto y espaciadores.
             */
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Padding interno general
                    .verticalScroll(rememberScrollState()), // Habilitar scroll vertical
                verticalArrangement = Arrangement.Top, // Alineación superior
                horizontalAlignment = Alignment.Start // Alineación a la izquierda
            ) {

                /**
                 * Imagen principal: Elemento decorativo en la parte superior.
                 */
                val image: Painter = painterResource(id = R.drawable.elefantecumpleanero)
                Image(
                    painter = image,
                    contentDescription = "Imagen de bienvenida",
                    modifier = Modifier.fillMaxWidth() // Ancho completo
                )

                Spacer(modifier = Modifier.height(32.dp)) // Espacio entre la imagen y los campos de texto

                /**
                 * Campo de texto: Nombre
                 * Incluye un fondo degradado oscuro, colores personalizados y bordes redondeados.
                 */
                TextField(
                    value = firstName, // Valor del campo
                    onValueChange = { firstName = it }, // Actualización del valor
                    label = {
                        Text(firstNameText) // Texto dinámico del label
                    },
                    modifier = Modifier
                        .fillMaxWidth() // Ancho completo del campo
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xFF222831), // Color oscuro superior
                                    Color(0xFF393E46)  // Color oscuro inferior
                                )
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes redondeados
                        ),
                    shape = RoundedCornerShape(16.dp), // Bordes redondeados del campo
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Color del texto enfocado (neón turquesa)
                        unfocusedTextColor = Color(0xFFEEEEEE), // Color del texto desenfocado (gris claro)
                        cursorColor = Color(0xFF00FFF5), // Color del cursor (neón turquesa)
                        focusedLabelColor = Color(0xFF00FFF5), // Color del label enfocado (neón turquesa)
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Color del label desenfocado (gris claro)
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido enfocado
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo desenfocado
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior enfocada
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior desenfocada
                    )
                )

                /**
                 * Espacio entre los campos anteriores y el siguiente campo de texto.
                 */
                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Campo de texto: Apellido
                 * Utiliza un fondo degradado oscuro y bordes redondeados.
                 */
                TextField(
                    value = lastName, // Valor actual del campo
                    onValueChange = { lastName = it }, // Actualización del valor
                    label = { Text(lastNameText) }, // Texto del label dinámico
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xFF222831), // Color oscuro superior
                                    Color(0xFF393E46)  // Color oscuro inferior
                                )
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto en turquesa neón cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta en neón cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido enfocado
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo desenfocado
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior enfocada
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior desenfocada
                    )
                )

                /**
                 * Espacio entre los campos de texto.
                 */
                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Campo de texto: Fecha de nacimiento
                 * Incluye un teclado numérico para facilitar la entrada de datos.
                 */
                TextField(
                    value = birthDate, // Valor actual del campo
                    onValueChange = { birthDate = it }, // Actualización del valor
                    label = { Text(birthDateText) }, // Texto del label dinámico
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), // Tipo de teclado
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xFF222831), // Color oscuro superior
                                    Color(0xFF393E46)  // Color oscuro inferior
                                )
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto en turquesa neón cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta en neón cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido enfocado
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo desenfocado
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior enfocada
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior desenfocada
                    )
                )

                /**
                 * Espacio entre los campos de texto.
                 */
                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Menú desplegable: Género
                 * Utiliza un gradiente oscuro y opciones dinámicas.
                 */
                var expanded by remember { mutableStateOf(false) } // Estado para controlar la apertura del menú
                // Opciones del menú con los valores a enviar al backend y los textos a mostrar
                val genderOptions = listOf(
                    "male" to maleText,
                    "female" to femaleText,
                    "other" to otherText
                )

                ExposedDropdownMenuBox(
                    expanded = expanded, // Estado del menú desplegable
                    onExpandedChange = { expanded = !expanded } // Alternar apertura/cierre del menú
                ) {
                    /**
                     * Caja envolvente del campo desplegable.
                     * Aplica un fondo degradado y redondeado.
                     */
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(Color(0xFF222831), Color(0xFF393E46)) // Degradado oscuro
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryEditable, // Tipo de anclaje del menú
                                enabled = true
                            )
                    ) {

                        /**
                         * Campo de texto desplegable
                         * Solo lectura, ya que el valor se selecciona desde el menú.
                         */
                        TextField(
                            value = selectedGender, // Valor seleccionado
                            onValueChange = { }, // No permite edición directa
                            readOnly = true, // Solo lectura
                            label = { Text(genderText) }, // Texto del label dinámico
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Expandir menú",
                                    tint = Color.White
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp), // Bordes redondeados
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF00FFF5), // Texto en turquesa neón cuando está enfocado
                                unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                                cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                                focusedLabelColor = Color(0xFF00FFF5), // Etiqueta en neón cuando está enfocado
                                unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris cuando no está enfocado
                                focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido enfocado
                                unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo desenfocado
                                focusedIndicatorColor = Color.Transparent, // Sin línea inferior enfocada
                                unfocusedIndicatorColor = Color.Transparent // Sin línea inferior desenfocada
                            )
                        )
                    }

                    /**
                     * Menú desplegable de opciones de género.
                     * Incluye un degradado vertical con tonos oscuros y un diseño futurista.
                     */
                    ExposedDropdownMenu(
                        expanded = expanded, // Estado del menú desplegable
                        onDismissRequest = { expanded = false }, // Acción al hacer clic fuera del menú
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color(0xFFC5687D), // Color superior del degradado
                                        Color(0xFFCE6F83)  // Color inferior del degradado
                                    )
                                )
                            )
                    ) {
                        /**
                         * Iteración sobre las opciones de género.
                         * Cada elemento del menú se convierte en una opción seleccionable.
                         */
                        genderOptions.forEach { (backendValue, displayText) ->
                            DropdownMenuItem(
                                text = { Text(displayText, color = Color.White) },
                                onClick = {
                                    selectedGender = backendValue // Este es el valor que se enviará al backend
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                /**
                 * Espacio entre el menú desplegable y el siguiente campo.
                 */
                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Campo de texto: Correo Electrónico
                 * Utiliza un fondo degradado oscuro con bordes redondeados.
                 */
                TextField(
                    value = email, // Valor actual del campo
                    onValueChange = { email = it }, // Actualización del valor
                    label = { Text(emailText) }, // Texto del label dinámico
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), // Tipo de teclado
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xFF222831), // Color oscuro superior
                                    Color(0xFF393E46)  // Color oscuro inferior
                                )
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto turquesa cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta turquesa cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris claro cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo más oscuro sin foco
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior cuando no está enfocado
                    )
                )

                /**
                 * Espacio entre el campo de correo electrónico y el siguiente campo.
                 */
                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Campo de texto: Contraseña
                 * Se aplica un visualTransformation para ocultar la contraseña.
                 */
                TextField(
                    value = password, // Valor actual del campo
                    onValueChange = { password = it }, // Actualización del valor
                    label = { Text(passwordText) }, // Texto del label dinámico
                    visualTransformation = PasswordVisualTransformation(), // Oculta el texto ingresado
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // Teclado de tipo contraseña
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xFF222831), // Color oscuro superior
                                    Color(0xFF393E46)  // Color oscuro inferior
                                )
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto turquesa cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta turquesa cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris claro cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo más oscuro sin foco
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior cuando no está enfocado
                    )
                )

                /**
                 * Espacio entre el campo de contraseña y el campo de confirmación de contraseña.
                 */
                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Campo de texto: Confirmar Contraseña
                 * Utiliza un visualTransformation para ocultar el texto ingresado.
                 */
                TextField(
                    value = confirmPassword, // Valor actual del campo
                    onValueChange = { confirmPassword = it }, // Actualización del valor
                    label = { Text(confirmPasswordText) }, // Texto del label dinámico
                    visualTransformation = PasswordVisualTransformation(), // Oculta el texto ingresado
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // Teclado tipo contraseña
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xFF222831), // Color oscuro superior
                                    Color(0xFF393E46)  // Color oscuro inferior
                                )
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes redondeados
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF00FFF5), // Texto turquesa cuando está enfocado
                        unfocusedTextColor = Color(0xFFEEEEEE), // Texto gris claro cuando no está enfocado
                        cursorColor = Color(0xFF00FFF5), // Cursor en color neón
                        focusedLabelColor = Color(0xFF00FFF5), // Etiqueta turquesa cuando está enfocado
                        unfocusedLabelColor = Color(0xFFEEEEEE), // Etiqueta gris claro cuando no está enfocado
                        focusedContainerColor = Color(0xFFF66D8A).copy(alpha = 0.7f), // Fondo oscuro translúcido
                        unfocusedContainerColor = Color(0xFFDD97B3).copy(alpha = 0.5f), // Fondo más oscuro sin foco
                        focusedIndicatorColor = Color.Transparent, // Sin línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent // Sin línea inferior cuando no está enfocado
                    )
                )

                /**
                 * Espacio entre el campo de confirmación de contraseña y el botón de creación de cuenta.
                 */
                Spacer(modifier = Modifier.height(32.dp))

                /**
                 * Botón para crear cuenta.
                 * Incluye un degradado lineal y un efecto de sombra.
                 */
                Button(
                    onClick = {
                        // Primero, validamos todos los campos
                        val error = validarRegistro(
                            firstName,
                            lastName,
                            birthDate,
                            selectedGender,
                            email,
                            password,
                            confirmPassword,
                            emptyList()
                        )

                        // Si hay un error, mostramos el mensaje y salimos del bloque onClick
                        if (error != null) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        // Verificar si el usuario ya existe
                        userViewModel.checkUserExists(
                            email = email,
                            onResult = { userExists ->
                                if (userExists) {
                                    // Mostrar mensaje de usuario existente
                                    Toast.makeText(context, userExistsText, Toast.LENGTH_SHORT).show()
                                } else {
                                    // Crea la solicitud de registro
                                    val request = RegisterRequest(
                                        firstName = firstName,
                                        lastName = lastName,
                                        birthDate = birthDate,
                                        gender = selectedGender,
                                        email = email,
                                        password = password
                                    )

                                    // Envía la solicitud al ViewModel
                                    userViewModel.registerUser(
                                        request = request,
                                        onSuccess = {
                                            navController?.navigate("registration_success") {
                                                popUpTo("register") { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        },
                                        onFailure = { errorMsg ->
                                            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                                        }
                                    )
                                }
                            },
                            onError = { errorMsg ->
                                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFFAD4962), // Degradado desde el color base
                                    ButtonColor  // Color definido para el botón
                                )
                            ),
                            shape = RoundedCornerShape(16.dp) // Bordes redondeados
                        )
                        .shadow(
                            elevation = 8.dp, // Elevación para crear un efecto de sombra
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = Color.Black.copy(alpha = 0.2f), // Color de la sombra
                            spotColor = Color.Black.copy(alpha = 0.1f)    // Color del contorno de la sombra
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent // Transparente para mostrar el degradado
                    )
                ) {
                    /**
                     * Texto del botón de crear cuenta.
                     * Se utiliza el texto dinámico traducido.
                     */
                    Text(createAccountText, color = Color.White)
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

