package com.example.littleelephant.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
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
            val buttonColor = when {
                selectedOption == null -> ButtonDefaults.buttonColors()
                option == question.correctAnswer -> ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                option == selectedOption -> ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                else -> ButtonDefaults.buttonColors()
            }

            Button(
                onClick = { if (selectedOption == null) selectedOption = option },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = buttonColor,
                enabled = selectedOption == null
            ) {
                Text(text = option)
            }
        }

        if (selectedOption != null) {
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

