package com.example.littleelephant.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.UserViewModel
import com.example.littleelephant.data.TranslationManager
import com.example.littleelephant.data.dataStore
import com.example.littleelephant.naviagtion.UserSessionManager
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navController: NavHostController,
    viewModel: UserViewModel
) {
    val context = LocalContext.current

    // --- VARIABLES DE TEXTO (TRADUCCIÓN) ---
    // Variables mutables para los textos traducidos
    var titleText by remember { mutableStateOf(TranslationManager.getString("progress_title")) }
    var percentageText by remember { mutableStateOf(TranslationManager.getString("progress_percentage")) }
    var allUnlockedText by remember { mutableStateOf(TranslationManager.getString("all_ecosystems_unlocked")) }
    var loadingText by remember { mutableStateOf(TranslationManager.getString("loading")) }
    var backText by remember { mutableStateOf(TranslationManager.getString("back")) }

    /**
     * Función para actualizar los textos según el idioma seleccionado.
     */
    fun updateTexts() {
        titleText = TranslationManager.getString("progress_title")
        percentageText = TranslationManager.getString("progress_percentage")
        allUnlockedText = TranslationManager.getString("all_ecosystems_unlocked")
        loadingText = TranslationManager.getString("loading")
        backText = TranslationManager.getString("back")
    }

    /**
     * Cargar el idioma inicial al entrar a la pantalla.
     */
    LaunchedEffect(Unit) {
        val lang = context.dataStore.data.first()[stringPreferencesKey("language")] ?: "es"
        TranslationManager.loadLanguage(context, lang)
        updateTexts()
    }

    // --- DATOS DEL USUARIO Y ECOSISTEMAS ---
    val email = remember { UserSessionManager.getEmail(context) }
    val ecosystems by viewModel.ecosystems.collectAsState()
    val userData = viewModel.userData.value

    // --- CONTROL DE ANIMACIÓN ---
    var startAnimation by remember { mutableStateOf(false) }

    /**
     * Cargar datos del usuario y ecosistemas al iniciar la pantalla.
     */
    LaunchedEffect(key1 = email, key2 = true) {
        email?.let { viewModel.fetchUserByEmailForProfile(it) }
        viewModel.fetchAllEcosystems()
    }

    // --- CÁLCULO DEL PROGRESO ---
    val totalLevels = ecosystems.size
    val unlockedLevelsCount = userData?.unlockedLevels?.size ?: 0

    // Progreso calculado basado en niveles desbloqueados
    val progress = if (totalLevels > 0) {
        unlockedLevelsCount.toFloat() / totalLevels
    } else {
        0f
    }

    // Porcentaje de progreso redondeado
    val percentage = (progress.coerceIn(0f, 1f) * 100).toInt()

    // Verificación de si todos los niveles han sido desbloqueados
    val hasCompletedAll = unlockedLevelsCount == totalLevels

    /**
     * Iniciar animación al completar todos los ecosistemas.
     */
    LaunchedEffect(hasCompletedAll) {
        if (hasCompletedAll) {
            startAnimation = true
        }
    }

    // Animación de escala cuando se completa todo
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleSpringAnimation"
    )

    /**
     * Estructura principal del Scaffold.
     */
    Scaffold(
        topBar = {
            // Barra superior con título y botón de retroceso
            TopAppBar(
                title = { Text(titleText, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = backText,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF395173))
            )
        }
    ) { paddingValues ->

        /**
         * Contenido principal: Se muestra cuando hay datos del usuario disponibles.
         */
        if (userData != null && totalLevels > 0) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xFFE4F3F6))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Imagen ilustrativa del progreso
                Image(
                    painter = painterResource(id = R.drawable.elefanteconsombrero),
                    contentDescription = "Imagen de progreso",
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Texto de porcentaje de progreso
                Text(
                    text = "$percentage% $percentageText",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Barra de progreso visual
                LinearProgressIndicator(
                    progress = progress.coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp),
                    color = if (hasCompletedAll) Color(0xFFB7FC5E) else Color(0xFF57c9da),
                    trackColor = Color(0xFFD9D9D9)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Mensaje de felicitación cuando todos los ecosistemas han sido desbloqueados
                AnimatedVisibility(visible = hasCompletedAll) {
                    Text(
                        text = allUnlockedText,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF395173),
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(scale),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        /**
         * Estado de carga: Se muestra mientras se cargan los datos del usuario.
         */
        else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = loadingText)
                }
            }
        }
    }
}

