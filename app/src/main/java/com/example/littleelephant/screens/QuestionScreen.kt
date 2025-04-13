package com.example.littleelephant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFDADFF8), Color(0xFFDFF8FC), Color(0xFFF8BBD0))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            question.options.forEach { option ->
                val isCorrect = option == question.correctAnswer
                val isSelected = option == selectedOption

                val baseColor = when {
                    selectedOption == null -> Color(0xFF7986CB)
                    isSelected && isCorrect -> Color(0xFF4CAF50)
                    isSelected && !isCorrect -> Color(0xFFF44336)
                    isCorrect -> Color(0xFF4CAF50)
                    else -> Color(0xFFBDBDBD)
                }

                val softBrush = Brush.horizontalGradient(
                    colors = listOf(
                        baseColor.copy(alpha = 0.9f),
                        baseColor
                    )
                )

                Button(
                    onClick = { if (selectedOption == null) selectedOption = option },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = softBrush,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                        )
                    }
                }
            }

            if (selectedOption != null) {
                val isCorrect = selectedOption == question.correctAnswer
                val feedbackText = if (isCorrect) "✅ ¡Correcto!" else "❌ Incorrecto. La respuesta era: ${question.correctAnswer}"

                Text(
                    text = feedbackText,
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.align(Alignment.End),
                    elevation = ButtonDefaults.buttonElevation(10.dp)
                ) {
                    Text("Siguiente")
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
                }
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

