package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.EcosystemViewModel
import com.example.littleelephant.apiRest.Question
import com.example.littleelephant.data.TranslationManager
import com.example.littleelephant.data.dataStore
import com.example.littleelephant.naviagtion.UserSessionManager
import kotlinx.coroutines.flow.first

@Composable
fun QuestionFlowScreen(
    navController: NavController,
    ecosystemName: String,
    viewModel: EcosystemViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val context = LocalContext.current

    // --- VARIABLES DE TEXTO (TRADUCCIÓN) ---
    // Variables mutables para los textos traducidos
    var ecosystemNotFoundText by remember { mutableStateOf(TranslationManager.getString("ecosystem_not_found")) }

    /**
     * Función para actualizar los textos según el idioma seleccionado.
     */
    fun updateTexts() {
        ecosystemNotFoundText = TranslationManager.getString("ecosystem_not_found")
    }

    /**
     * Cargar el idioma inicial al entrar a la pantalla.
     */
    LaunchedEffect(Unit) {
        val lang = context.dataStore.data.first()[stringPreferencesKey("language")] ?: "es"
        TranslationManager.loadLanguage(context, lang)
        updateTexts()
    }

    var hasWrongAnswer by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var hasUnlockedNext by remember { mutableStateOf(false) }

    val ecosystems = viewModel.ecosystems.value
    val unlockedLevels = viewModel.unlockedLevels.value

    val ecosystem = ecosystems.find { it.name == ecosystemName }
    val currentIndexInList = ecosystems.indexOfFirst { it.name == ecosystemName }

    /**
     * Si el ecosistema no se encuentra, muestra el texto traducido.
     */
    if (ecosystem == null) {
        Text(
            text = ecosystemNotFoundText,
            modifier = Modifier.padding(32.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        return
    }

    /**
     * Si hay preguntas pendientes, muestra la pantalla de preguntas.
     */
    if (currentIndex < ecosystem.questions.size) {
        val currentQuestion: Question = ecosystem.questions[currentIndex]
        key(currentQuestion) {
            QuestionScreen(
                question = currentQuestion,
                onNext = { currentIndex++ },
                navController = navController,
                onWrongAnswer = { hasWrongAnswer = true }
            )
        }
    }
    /**
     * Si no hay preguntas pendientes, muestra la pantalla de finalización o la pantalla de reintento.
     */
    else {
        if (hasWrongAnswer) {
            RetryScreen(
                onRetry = {
                    currentIndex = 0
                    hasWrongAnswer = false
                },
                onBackToLevels = {
                    navController.popBackStack()
                }
            )
        } else {
            LaunchedEffect(Unit) {
                val nextLevel = currentIndexInList + 1
                val email = UserSessionManager.getEmail(context)

                /**
                 * Desbloquear el siguiente nivel si existe y no está desbloqueado.
                 */
                if (email != null &&
                    nextLevel < ecosystems.size &&
                    (nextLevel + 1) !in unlockedLevels &&
                    !hasUnlockedNext
                ) {
                    viewModel.unlockLevel(email, nextLevel + 1)
                    hasUnlockedNext = true
                }
            }

            /**
             * Si se ha completado el último ecosistema, muestra la pantalla de felicitaciones.
             * De lo contrario, muestra la pantalla de finalización.
             */
            if (currentIndexInList == ecosystems.lastIndex) {
                CongratulationsScreen(onBackToHome = { navController.popBackStack() })
            } else {
                FinishedScreen(onBackToLevels = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun FinishedScreen(onBackToLevels: () -> Unit) {

    val context = LocalContext.current

    // --- VARIABLES DE TEXTO (TRADUCCIÓN) ---
    var completedEcosystemText by remember { mutableStateOf(TranslationManager.getString("completed_ecosystem")) }
    var backToLevelsText by remember { mutableStateOf(TranslationManager.getString("back_to_levels")) }

    /**
     * Función para actualizar los textos según el idioma seleccionado.
     */
    fun updateTexts() {
        completedEcosystemText = TranslationManager.getString("completed_ecosystem")
        backToLevelsText = TranslationManager.getString("back_to_levels")
    }

    /**
     * Cargar el idioma inicial al entrar a la pantalla.
     */
    LaunchedEffect(Unit) {
        val lang = context.dataStore.data.first()[stringPreferencesKey("language")] ?: "es"
        TranslationManager.loadLanguage(context, lang)
        updateTexts()
    }

    // Fondo degradado
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE1F5FE),
            Color(0xFFFFF8E1),
            Color(0xFFFFEBEE)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Imagen decorativa
            Image(
                painter = painterResource(id = R.drawable.elefanterey),
                contentDescription = completedEcosystemText,
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Fit
            )

            // Texto de felicitación
            Text(
                text = completedEcosystemText,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            // Botón para volver a los ecosistemas
            Button(
                onClick = onBackToLevels,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(50), clip = true)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF5C5D83), // azul lavanda profundo
                                Color(0xFF696A91)  // azul grisáceo claro
                            )
                        ),
                        shape = RoundedCornerShape(50)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = backToLevelsText,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FinishedScreenPreview() {
    MaterialTheme {
        FinishedScreen(onBackToLevels = {})
    }
}

@Composable
fun RetryScreen(
    onRetry: () -> Unit,
    onBackToLevels: () -> Unit
) {

    val context = LocalContext.current

    // --- VARIABLES DE TEXTO (TRADUCCIÓN) ---
    var retryText by remember { mutableStateOf(TranslationManager.getString("retry")) }
    var backToLevelsText by remember { mutableStateOf(TranslationManager.getString("back_to_levels")) }
    var incorrectAnswerText by remember { mutableStateOf(TranslationManager.getString("incorrect_answer")) }

    /**
     * Función para actualizar los textos según el idioma seleccionado.
     */
    fun updateTexts() {
        retryText = TranslationManager.getString("retry")
        backToLevelsText = TranslationManager.getString("back_to_levels")
        incorrectAnswerText = TranslationManager.getString("incorrect_answer")
    }

    /**
     * Cargar el idioma inicial al entrar a la pantalla.
     */
    LaunchedEffect(Unit) {
        val lang = context.dataStore.data.first()[stringPreferencesKey("language")] ?: "es"
        TranslationManager.loadLanguage(context, lang)
        updateTexts()
    }

    // Fondo degradado
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFEBEE),
            Color(0xFFFCE4EC),
            Color(0xFFF8BBD0)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Imagen decorativa
            Image(
                painter = painterResource(id = R.drawable.elefantemolesto),
                contentDescription = incorrectAnswerText,
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Fit
            )

            // Texto de respuesta incorrecta
            Text(
                text = incorrectAnswerText,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error
            )

            // Botón Reintentar
            Button(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(50), clip = true)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFC41B1B), Color(0xFFC41B45))
                        ),
                        shape = RoundedCornerShape(50)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = retryText,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            // Botón Volver a los ecosistemas
            Button(
                onClick = onBackToLevels,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(50), clip = true)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFC41B1B), Color(0xFFC41B45))
                        ),
                        shape = RoundedCornerShape(50)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = backToLevelsText,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RetryScreenPreview() {
    MaterialTheme {
        RetryScreen(
            onRetry = {},
            onBackToLevels = {}
        )
    }
}







