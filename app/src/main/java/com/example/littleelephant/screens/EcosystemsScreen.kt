package com.example.littleelephant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.littleelephant.apiRest.EcosystemViewModel
import com.example.littleelephant.naviagtion.AppScreens
import com.example.littleelephant.naviagtion.UserSessionManager
import com.example.littleelephant.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcosystemsScreen(
    navController: NavHostController,
    viewModel: EcosystemViewModel = viewModel()
) {
    val context = LocalContext.current
    val unlockedLevels = viewModel.unlockedLevels.value

    // Cargar niveles desbloqueados del usuario al iniciar
    LaunchedEffect(Unit) {
        val email = UserSessionManager.getEmail(context)
        if (email != null) {
            viewModel.fetchUserByEmail(email)
        }
    }

    val ecosystems = viewModel.ecosystems.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ecosistemas", color = Color.White, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = {}) {
                        AsyncImage(
                            model = "https://via.placeholder.com/64", // ← imagen superior opcional
                            contentDescription = "Icono Little Elephant",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueOne)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BlueOne
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigate(AppScreens.PreferencesScreen.route) }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Ajustes",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { navController.navigate(AppScreens.ProgressScreen.route) }) {
                        Icon(
                            imageVector = Icons.Default.DoneOutline,
                            contentDescription = "Progreso",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { navController.navigate(AppScreens.PreferencesScreen.route) }) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Idioma",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { navController.navigate(AppScreens.LoginScreen.route) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Salir",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color(0xFFF4F2F2))
        ) {
            // 🎨 Color personalizado según el nombre del ecosistema
            itemsIndexed(ecosystems) { index, ecosystem ->
                val isUnlocked = (index + 1) in unlockedLevels
                val color = when (ecosystem.name) {
                    "Farm" -> Color(0xFFA54239) // amarillo dorado
                    "Ocean" -> Color(0xFF98DAF1) // azul oceánico
                    "Jungle" -> Color(0xFFC6D89A) // verde selva
                    "Savannah" -> Color(0xFFC76354) // naranja sabana
                    "Swamp" -> Color(0xFF4D95BB) // verde pantano
                    else -> Color.LightGray
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(100.dp)
                        .alpha(if (isUnlocked) 1f else 0.5f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = color),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    onClick = {
                        if (isUnlocked) {
                            navController.navigate(AppScreens.QuestionScreen.createRoute(ecosystem.name))
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Text(
                            text = ecosystem.name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 5f,
                                    offset = Offset(0f, 5f)
                                )
                            )
                        )

                        AsyncImage(
                            model = ecosystem.image, // ← imagen desde el backend
                            contentDescription = "Imagen del ecosistema",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.BottomCenter)
                        )

                        if (!isUnlocked) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Bloqueado",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(28.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}




