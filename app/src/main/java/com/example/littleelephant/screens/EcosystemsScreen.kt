package com.example.littleelephant.screens

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.littleelephant.R
import com.example.littleelephant.apiRest.EcosystemViewModel
import com.example.littleelephant.data.Ecosystem
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

    // Mapas para asignar color e imagen a cada ecosistema por nombre
    val ecosystemColorMap = mapOf(
        "Farm" to granja,
        "Ocean" to oceano,
        "Jungle" to selva,
        "Savannah" to sabana,
        "Swamp" to pantano
    )

    val ecosystemImageMap = mapOf(
        "Farm" to R.drawable.granja,
        "Ocean" to R.drawable.oceano,
        "Jungle" to R.drawable.selva,
        "Savannah" to R.drawable.sabana,
        "Swamp" to R.drawable.pantano
    )

    // Traer datos del usuario (email) para obtener niveles desbloqueados
    LaunchedEffect(Unit) {
        val email = UserSessionManager.getEmail(context)
        if (email != null) {
            viewModel.fetchUserByEmail(email)
        }
    }

    // Mapear ecosistemas del backend al modelo visual local
    val ecosystems = viewModel.ecosystems.value.map { eco ->
        Ecosystem(
            name = eco.name,
            color = ecosystemColorMap[eco.name] ?: Color.Gray,
            imageResId = ecosystemImageMap[eco.name] ?: R.drawable.elefantelenguaafuera
        )
    }

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
                containerColor = BlueOne
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {IconButton(onClick = { navController.navigate("preferences_screen") }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Ajustes",
                        tint = Color.White
                    )
                }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { navController.navigate("preferences_screen") }) {
                        Icon(imageVector = Icons.Default.DoneOutline,
                            contentDescription = "Idioma",
                            tint = Color.White

                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { navController.navigate("preferences_screen") }) {
                        Icon(imageVector = Icons.Default.Language,
                            contentDescription = "Idioma",
                            tint = Color.White

                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { navController.navigate("login_screen") }) {
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
            itemsIndexed(ecosystems) { index, ecosystem ->
                val isUnlocked = (index + 1) in unlockedLevels

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EcosystemsScreenPreview() {
    val ecosystems = listOf(
        Ecosystem("Farm", granja, R.drawable.granja),
        Ecosystem("Ocean", oceano, R.drawable.oceano),
        Ecosystem("Jungle", selva, R.drawable.selva)
    )
    val unlockedLevels = listOf(0, 1)

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
                colors = CardDefaults.cardColors(containerColor = ecosystem.color),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
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



