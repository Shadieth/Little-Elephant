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
import com.example.littleelephant.data.Level
import com.example.littleelephant.naviagtion.AppScreens
import com.example.littleelephant.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreenLevels(navController: NavHostController) {
    // Lista de niveles con los colores definidos
    val levels = listOf(
        Level("Nivel 1", "Farm", granja,  R.drawable.granja),
        Level("Nivel 2", "Ocean", oceano, R.drawable.oceano),
        Level("Nivel 3", "Jungle", selva, R.drawable.selva),
        Level("Nivel 4", "Savannah", sabana, R.drawable.sabana),
        Level("Nivel 5", "Swamp", pantano, R.drawable.pantano)
    )

    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        "Niveles",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(
                        onClick = { /* Acción */ },
                        modifier = Modifier.size(90.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.elefanteleyendo),
                            contentDescription = "Icono Little Elephant",
                            modifier = Modifier.size(64.dp), // Ajusta el tamaño aquí
                            contentScale = ContentScale.Fit // Ajusta cómo se escala la imagen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueOne, // Color del fondo de la TopAppBar
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BlueOne, // Color de fondo del BottomAppBar
                actions = {
                    IconButton(onClick = { navController.navigate("login_screen") }) {
                        Icon(Icons.Default.Home, contentDescription = "Inicio")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { navController.navigate("preferences_screen")}) {
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
                .background(color = Color(0xFFF4F2F2)),
        ) {
            items(levels.size) { index ->
                val level = levels[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(100.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = level.color
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    onClick = { navController.navigate(AppScreens.QuestionScreen.createRoute(level.location)) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                        ,
                        contentAlignment = Alignment.TopStart,
                    ) {
                        Text(
                            style = TextStyle(
                                fontSize = 20.sp,
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 5f,
                                    offset = Offset(0f, 5f)
                                )
                            ),
                            text = level.name, // Mostramos el nombre del nivel
                            color = Color.White,
                            fontWeight = FontWeight.Bold,

                        )
                        Image(
                            painter = painterResource(id = level.imageResId),
                            contentDescription = "Imagen del nivel",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.BottomCenter)
                        )
                        Text(
                            style = TextStyle(
                                fontSize = 20.sp,
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 5f,
                                    offset = Offset(0f, 5f)
                                )
                            ),
                            modifier = Modifier.align(Alignment.BottomEnd),
                            text = level.location, // Mostramos el nombre del nivel
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
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
    // Mismo contenido que SecondScreenLevels, pero sin navegación real
    val levels = listOf(
        Level("Nivel 1", "Granja", granja, R.drawable.granja),
        Level("Nivel 2", "Océano", oceano, R.drawable.oceano),
        Level("Nivel 3", "Selva", selva, R.drawable.selva),
        Level("Nivel 4", "Sabana", sabana, R.drawable.sabana),
        Level("Nivel 5", "Pantano", pantano, R.drawable.pantano)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Niveles", color = Color.White, fontWeight = FontWeight.Bold)
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
            items(levels.size) { index ->
                val level = levels[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(100.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = level.color),
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
                            text = level.name,
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
                            painter = painterResource(id = level.imageResId),
                            contentDescription = "Imagen del nivel",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.BottomCenter)
                        )
                        Text(
                            text = level.location,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.BottomEnd),
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 5f,
                                    offset = Offset(0f, 5f)
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SecondScreenLevelsPreview() {
    LittleElephantTheme {
        SecondScreenLevelsPreviewContent()
    }
}



