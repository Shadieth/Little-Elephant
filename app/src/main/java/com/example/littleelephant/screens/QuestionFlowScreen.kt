package com.example.littleelephant.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littleelephant.apiRest.EcosystemViewModel
import com.example.littleelephant.apiRest.Question
import com.example.littleelephant.naviagtion.UserSessionManager

@Composable
fun QuestionFlowScreen(
    navController: NavController,
    ecosystemName: String,
    viewModel: EcosystemViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
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

    var currentIndex by remember { mutableIntStateOf(0) }
    var hasUnlockedNext by remember { mutableStateOf(false) }

    if (currentIndex < ecosystem.questions.size) {
        val currentQuestion: Question = ecosystem.questions[currentIndex]
        key(currentQuestion) {
            QuestionScreen(
                question = currentQuestion,
                onNext = { currentIndex++ },
                navController = navController
            )
        }
    } else {
        // Desbloqueo del siguiente ecosistema (solo 1 vez)
        LaunchedEffect(Unit) {
            val nextLevel = currentIndexInList + 1
            val email = UserSessionManager.getEmail(context)
            if (
                email != null &&
                nextLevel < ecosystems.size &&
                (nextLevel + 1) !in unlockedLevels &&
                !hasUnlockedNext
            ) {
                viewModel.unlockLevel(email, nextLevel + 1) // Niveles en backend empiezan desde 1
                hasUnlockedNext = true
            }
        }

        if (currentIndexInList == ecosystems.lastIndex) {
            // Último ecosistema completado
            CongratulationsScreen(onBackToHome = { navController.popBackStack() })
        } else {
            FinishedScreen(onBackToLevels = { navController.popBackStack() })
        }

    }
}

@Composable
fun FinishedScreen(onBackToLevels: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¡Has completado este ecosistema!",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBackToLevels) {
            Text("Volver a los niveles")
        }
    }
}






