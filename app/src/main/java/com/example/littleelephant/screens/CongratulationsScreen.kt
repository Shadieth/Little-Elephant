package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littleelephant.R

@Composable
fun CongratulationsScreen(onBackToHome: () -> Unit) {
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE0F7FA), // azul agua claro
            Color(0xFFF1F8E9), // verde pastel
            Color(0xFFFFF3E0)  // crema cálido
        )
    )

    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF5C5D83), // azul lavanda profundo
            Color(0xFF6A6B96)  // lavanda suave brillante
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
                painter = painterResource(id = R.drawable.elefanteconglobo),
                contentDescription = "Felicitaciones",
                modifier = Modifier
                    .size(300.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "🎉 ¡Felicidades!",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF4A148C) // púrpura elegante y vibrante
            )

            Text(
                text = "Has completado todos los ecosistemas 🌎",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onBackToHome,
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(50), clip = true),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = buttonGradient,
                            shape = RoundedCornerShape(50)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Volver al inicio",
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CongratulationsScreenPreview() {
    MaterialTheme {
        CongratulationsScreen(onBackToHome = {})
    }
}


