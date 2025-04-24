package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.UserViewModel
import com.example.littleelephant.naviagtion.UserSessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navController: NavHostController,
    viewModel: UserViewModel
) {
    val context = LocalContext.current
    val email = remember { UserSessionManager.getEmail(context) }

    val ecosystems by viewModel.ecosystems.collectAsState()
    val userData = viewModel.userData.value

    // Cargar datos al iniciar
    LaunchedEffect(Unit) {
        email?.let { viewModel.fetchUserByEmailForProfile(it) }
        viewModel.fetchAllEcosystems()
    }

    val totalLevels = ecosystems.size
    val currentLevel = userData?.unlockedLevels?.size ?: 0
    val progress = if (totalLevels > 0) currentLevel.toFloat() / totalLevels else 0f
    val percentage = (progress * 100).toInt()
    val hasCompletedAll = progress >= 1f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progreso", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF395173))
            )
        }
    ) { paddingValues ->
        if (userData != null && totalLevels > 0) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xFFE4F3F6))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.elefanteconsombrero),
                    contentDescription = "Imagen de progreso",
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "$percentage% completado",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = progress.coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp),
                    color = Color(0xFF57c9da),
                    trackColor = Color(0xFFD9D9D9)
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (hasCompletedAll) {
                    Text(
                        text = "🎉 ¡Felicidades! Has completado todos los ecosistemas.",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF395173)
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}


