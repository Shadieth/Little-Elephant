package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littleelephant.R

@Composable
fun CongratulationsScreen(onBackToHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🎨 Imagen decorativa
        Image(
            painter = painterResource(id = R.drawable.elefanteconglobo), // cambia esto por tu imagen real
            contentDescription = "Felicitaciones",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 24.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "🎉 ¡Felicidades!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Has completado todos los ecosistemas 🐘🌎",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onBackToHome) {
            Text("Volver al inicio")
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

