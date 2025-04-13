package com.example.littleelephant.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littleelephant.apiRest.EcosystemViewModel
import com.example.littleelephant.apiRest.Question

@Composable
fun QuestionFlowScreen(
    navController: NavController,
    ecosystemName: String,
    viewModel: EcosystemViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val ecosystems = viewModel.ecosystems.value
    val ecosystem = ecosystems.find { it.name == ecosystemName }

    if (ecosystem == null) {
        Text(
            text = "Ecosistema no encontrado",
            modifier = Modifier.padding(32.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        return
    }

    var currentIndex by remember { mutableIntStateOf(0) }

    if (currentIndex < ecosystem.questions.size) {
        val currentQuestion: Question = ecosystem.questions[currentIndex]
        QuestionScreen(
            question = currentQuestion,
            onNext = {
                currentIndex++
            },
            navController = navController
        )
    } else {
        FinishedScreen(onBackToLevels = { navController.popBackStack() })
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





