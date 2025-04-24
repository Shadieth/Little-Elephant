package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.EcosystemViewModel
import com.example.littleelephant.apiRest.Question
import com.example.littleelephant.naviagtion.UserSessionManager

@Composable
fun QuestionFlowScreen(
    navController: NavController,
    ecosystemName: String,
    viewModel: EcosystemViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var hasWrongAnswer by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var hasUnlockedNext by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val ecosystems = viewModel.ecosystems.value
    val unlockedLevels = viewModel.unlockedLevels.value

    val ecosystem = ecosystems.find { it.name == ecosystemName }
    val currentIndexInList = ecosystems.indexOfFirst { it.name == ecosystemName }

    if (ecosystem == null) {
        Text(
            text = "Ecosistema no encontrado",
            modifier = Modifier.padding(32.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        return
    }

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
    } else {
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
                if (
                    email != null &&
                    nextLevel < ecosystems.size &&
                    (nextLevel + 1) !in unlockedLevels &&
                    !hasUnlockedNext
                ) {
                    viewModel.unlockLevel(email, nextLevel + 1)
                    hasUnlockedNext = true
                }
            }

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
            Image(
                painter = painterResource(id = R.drawable.elefanterey),
                contentDescription = "Ecosistema completado",
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "¡Has completado este ecosistema!",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

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
                    text = "Volver a los ecosistemas",
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFFFEBEE),
                        Color(0xFFFCE4EC),
                        Color(0xFFF8BBD0)
                    )
                )
            )
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Imagen decorativa
            Image(
                painter = painterResource(id = R.drawable.elefantemolesto), // 🐘 Usa tu imagen real aquí
                contentDescription = "Respuesta incorrecta",
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "❌ Debes responder correctamente todas las preguntas.",
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
                            colors = listOf(Color(0xFFC41B1B), Color(0xFFC41B45)) // tonos azul blush aesthetic
                        ),
                        shape = RoundedCornerShape(50)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // lo deja transparente para mostrar el brush
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Reintentar",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }


            // Botón Volver a los ecosistemas
            val blueBlushBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFC41B1B), Color(0xFFC41B45)) // tonos azules aesthetic
            )

            Button(
                onClick = onBackToLevels,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(50), clip = true)
                    .background(
                        brush = blueBlushBrush,
                        shape = RoundedCornerShape(50)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Volver a los ecosistemas",
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







