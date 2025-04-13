package com.example.littleelephant.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.littleelephant.apiRest.Question

@Composable
fun QuestionScreen(
    question: Question,
    onNext: () -> Unit = {},
    navController: NavController? = null
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AsyncImage(
            model = question.image,
            contentDescription = "Pregunta",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Seleccione la respuesta correcta",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        question.options.forEach { option ->
            val isCorrect = option == question.correctAnswer
            val isSelected = option == selectedOption

            val backgroundColor = when {
                selectedOption == null -> MaterialTheme.colorScheme.primary
                isSelected && isCorrect -> Color(0xFF4CAF50) // Verde
                isSelected && !isCorrect -> Color(0xFFF44336) // Rojo
                isCorrect -> Color(0xFF4CAF50) // Muestra la correcta si falló
                else -> Color(0xFFBDBDBD) // Neutro
            }

            Button(
                onClick = {
                    if (selectedOption == null) {
                        selectedOption = option
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = option)
            }
        }

        // ✅ Feedback opcional
        if (selectedOption != null) {
            val isAnswerCorrect = selectedOption == question.correctAnswer
            val feedbackText = if (isAnswerCorrect) {
                "✅ ¡Correcto!"
            } else {
                "❌ Incorrecto. La respuesta era: ${question.correctAnswer}"
            }

            Text(
                text = feedbackText,
                color = if (isAnswerCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onNext,
                shape = RoundedCornerShape(50),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Siguiente")
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionScreenPreview() {
    val sampleQuestion = Question(
        image = "https://example.com/images/frog.jpg", // puedes usar una URL válida si quieres ver imagen
        options = listOf("Toad", "Frog", "Lizard"),
        correctAnswer = "Frog"
    )

    MaterialTheme {
        QuestionScreen(question = sampleQuestion)
    }
}

