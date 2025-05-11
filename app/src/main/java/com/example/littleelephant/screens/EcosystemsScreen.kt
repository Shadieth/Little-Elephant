package com.example.littleelephant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.littleelephant.apiRest.EcosystemViewModel
import com.example.littleelephant.data.TranslationManager
import com.example.littleelephant.data.dataStore
import com.example.littleelephant.naviagtion.AppScreens
import com.example.littleelephant.naviagtion.UserSessionManager
import com.example.littleelephant.ui.theme.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcosystemsScreen(
    navController: NavHostController,
    viewModel: EcosystemViewModel = viewModel()
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val unlockedLevels = viewModel.unlockedLevels.value

    var showLanguageMenu by remember { mutableStateOf(false) }

    /**
     * Variables mutables para los textos que se actualizan al cambiar el idioma.
     */
    var titleText by remember { mutableStateOf(TranslationManager.getString("title_ecosystems")) }
    var spanishText by remember { mutableStateOf(TranslationManager.getString("spanish_text")) }
    var englishText by remember { mutableStateOf(TranslationManager.getString("english_text")) }

    /**
     * Actualiza los textos según el idioma seleccionado.
     */
    fun updateTexts() {
        titleText = TranslationManager.getString("title_ecosystems")
        spanishText = TranslationManager.getString("spanish_text")
        englishText = TranslationManager.getString("english_text")
    }

    /**
     * Función para cambiar el idioma y forzar la actualización de los textos.
     */
    fun changeLanguage(lang: String) {
        coroutineScope.launch {
            context.dataStore.edit { it[stringPreferencesKey("language")] = lang }
            TranslationManager.loadLanguage(context, lang)
            updateTexts()
        }
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
     * Cargar niveles desbloqueados del usuario al iniciar la pantalla.
     */
    LaunchedEffect(Unit) {
        val email = UserSessionManager.getEmail(context)
        if (email != null) {
            viewModel.fetchUserByEmail(email)
        }
    }

    val ecosystems = viewModel.ecosystems.value

    // Construcción del Scaffold que contiene el TopBar, BottomBar y el contenido principal.
    Scaffold(
        topBar = {
            /**
             * Barra superior con el título dinámico basado en el idioma seleccionado.
             */
            TopAppBar(
                title = {
                    Text(
                        titleText,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        AsyncImage(
                            model = "https://via.placeholder.com/64",
                            contentDescription = "Icono Little Elephant",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueOne)
            )
        },
        bottomBar = {
            /**
             * Barra inferior con navegación y selección de idioma.
             */
            BottomAppBar(containerColor = BlueOne) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigate(AppScreens.ProfileScreen.route) }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Perfil", tint = Color.White)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(onClick = { navController.navigate(AppScreens.ProgressScreen.route) }) {
                        Icon(Icons.Default.DoneOutline, contentDescription = "Progreso", tint = Color.White)
                    }

                    // Menú desplegable para cambiar el idioma
                    Box {
                        IconButton(onClick = { showLanguageMenu = true }) {
                            Icon(Icons.Default.Language, contentDescription = "Idioma", tint = Color.White)
                        }
                        DropdownMenu(expanded = showLanguageMenu, onDismissRequest = { showLanguageMenu = false }) {
                            DropdownMenuItem(
                                text = { Text(spanishText) },
                                onClick = {
                                    changeLanguage("es")
                                    showLanguageMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(englishText) },
                                onClick = {
                                    changeLanguage("en")
                                    showLanguageMenu = false
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(onClick = { navController.navigate(AppScreens.LoginScreen.route) }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Salir", tint = Color.White)
                    }
                }
            }
        }
    ) { innerPadding ->
        /**
         * Contenido principal: Lista de ecosistemas.
         */
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFDECAC7),   // Beige más claro
                            Color(0xFFD5BFBC),  // Beige claro
                            Color(0xFFBBA19D)  // Marrón oscuro
                        )
                    )
                )
        ) {
            itemsIndexed(ecosystems) { index, ecosystem ->
                val isUnlocked = (index + 1) in unlockedLevels
                val cardColor = when (ecosystem.name) {
                    "Farm" -> Color(0xFFA54239)
                    "Ocean" -> Color(0xFF98DAF1)
                    "Jungle" -> Color(0xFFC6D89A)
                    "Savannah" -> Color(0xFFC76354)
                    "Swamp" -> Color(0xFF4D95BB)
                    else -> Color.LightGray
                }

                /**
                 * Tarjeta de cada ecosistema.
                 */
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(100.dp)
                        .alpha(if (isUnlocked) 1f else 0.5f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    onClick = {
                        if (isUnlocked) {
                            navController.navigate(AppScreens.QuestionScreen.createRoute(ecosystem.name))
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Text(
                            text = ecosystem.name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 5f,
                                    offset = Offset(0f, 5f)
                                )
                            )
                        )

                        AsyncImage(
                            model = ecosystem.image,
                            contentDescription = "Imagen del ecosistema",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.BottomCenter)
                        )

                        if (!isUnlocked) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Bloqueado",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(28.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}





