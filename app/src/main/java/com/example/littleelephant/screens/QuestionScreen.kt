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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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
    navController: NavController? = null,
    onWrongAnswer: () -> Unit
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // 🟢 CENTRADO
        ) {
            AsyncImage(
                model = question.image,
                contentDescription = "Imagen de la pregunta",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Seleccione la respuesta correcta",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            question.options.forEach { option ->
                val isCorrect = option == question.correctAnswer
                val isSelected = option == selectedOption

                val baseColor = when {
                    selectedOption == null -> Color(0xFF307594)
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
                    onClick = {
                        if (selectedOption == null) {
                            selectedOption = option
                            if (option != question.correctAnswer) {
                                onWrongAnswer()
                            }
                        }
                    },
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
                            .background(brush = softBrush, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            if (selectedOption != null) {
                val isCorrect = selectedOption == question.correctAnswer
                val feedbackText = if (isCorrect) "✅ ¡Correcto!" else "❌ Incorrecto. La respuesta era: ${question.correctAnswer}"

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = feedbackText,
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
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
                // Dentro del bloque if (selectedOption != null)
                Spacer(modifier = Modifier.height(12.dp))

                val blueBlushBrush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFC41B1B), Color(0xFFC41B45))
                )
            }

            Spacer(modifier = Modifier.height(54.dp))

            val blueBlushBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFCC2626), Color(0xFFC90808))
            )

            Button(
                onClick = {
                    navController?.navigate("ecosystems_screen") {
                        popUpTo("question_screen") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(32.dp)
                    .shadow(2.dp, shape = RoundedCornerShape(30), clip = true)
                    .background(brush = blueBlushBrush, shape = RoundedCornerShape(30)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Volver",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = Color.White
                    )
                )
            }

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun QuestionScreenPreview() {
    val sampleQuestion = Question(
        image = "https://example.com/images/elephant.png", // puedes reemplazar por una imagen real
        options = listOf("Gato", "Elefante", "Perro"),
        correctAnswer = "Elefante"
    )

    MaterialTheme {
        QuestionScreen(
            question = sampleQuestion,
            onNext = {},
            onWrongAnswer = {},
            navController = null
        )
    }
}
