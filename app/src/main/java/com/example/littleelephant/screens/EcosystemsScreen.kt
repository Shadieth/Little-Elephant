package com.example.littleelephant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ecosistemas", color = Color.White, fontWeight = FontWeight.Bold)
                },
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
            items(ecosystems.size) { index ->
                val ecosystem = ecosystems[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(100.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ecosystem.color),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    onClick = {
                        navController.navigate(AppScreens.QuestionScreen.createRoute(ecosystem.name))
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
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreenLevelsPreviewContent() {
    val ecosystems = listOf(
        Ecosystem("Farm", granja, R.drawable.granja),
        Ecosystem("Ocean", oceano, R.drawable.oceano),
        Ecosystem("Jungle", selva, R.drawable.selva),
        Ecosystem("Savannah", sabana, R.drawable.sabana),
        Ecosystem("Swamp", pantano, R.drawable.pantano)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ecosistemas", color = Color.White, fontWeight = FontWeight.Bold)
                },
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
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Home, contentDescription = "Inicio")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = {}) {
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
            items(ecosystems.size) { index ->
                val ecosystem = ecosystems[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(100.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ecosystem.color),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    onClick = { /* Nada en preview */ }
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
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EcosystemsScreenPreview() {
    LittleElephantTheme {
        SecondScreenLevelsPreviewContent()
    }
}


