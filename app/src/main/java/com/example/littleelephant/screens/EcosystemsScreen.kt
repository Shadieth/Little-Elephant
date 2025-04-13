package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littleelephant.R
import com.example.littleelephant.data.Ecosystem
import com.example.littleelephant.naviagtion.AppScreens
import com.example.littleelephant.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcosystemsScreen(navController: NavHostController) {
    val ecosystems = listOf(
        Ecosystem("Farm", granja, R.drawable.granja),
        Ecosystem("Ocean", oceano, R.drawable.oceano),
        Ecosystem("Jungle", selva, R.drawable.selva),
        Ecosystem("Savannah", sabana, R.drawable.sabana),
        Ecosystem("Swamp", pantano, R.drawable.pantano)
    )

    val unlockedLevels = listOf(0, 1) // niveles desbloqueados

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ecosistemas", color = Color.White, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(id = R.drawable.elefanteleyendo),
                            contentDescription = "Icono Little Elephant",
                            modifier = Modifier.size(64.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueOne)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BlueOne,
                actions = {
                    IconButton(onClick = { navController.navigate("login_screen") }) {
                        Icon(Icons.Default.Home, contentDescription = "Inicio")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { navController.navigate("preferences_screen") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color(0xFFF4F2F2))
        ) {
            itemsIndexed(ecosystems) { index, ecosystem ->
                val isUnlocked = index in unlockedLevels

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(100.dp)
                        .alpha(if (isUnlocked) 1f else 0.5f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ecosystem.color),
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

                        Image(
                            painter = painterResource(id = ecosystem.imageResId),
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EcosystemsScreenPreview() {
    LittleElephantTheme {
        EcosystemsScreenPreviewContent()
    }
}

@Composable
fun EcosystemsScreenPreviewContent() {
    // Vista previa sin navegación
    val ecosystems = listOf(
        Ecosystem("Farm", granja, R.drawable.granja),
        Ecosystem("Ocean", oceano, R.drawable.oceano),
        Ecosystem("Jungle", selva, R.drawable.selva)
    )
    val unlockedLevels = listOf(0)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F2F2))
            .padding(16.dp)
    ) {
        itemsIndexed(ecosystems) { index, ecosystem ->
            val isUnlocked = index in unlockedLevels

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(100.dp)
                    .alpha(if (isUnlocked) 1f else 0.5f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ecosystem.color)
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

                    Image(
                        painter = painterResource(id = ecosystem.imageResId),
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

