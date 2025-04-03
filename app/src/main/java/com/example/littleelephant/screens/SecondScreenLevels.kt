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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littleelephant.R
import com.example.littleelephant.data.Level
import com.example.littleelephant.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreenLevels(navController: NavHostController? = null) {
    // Lista de niveles con los colores definidos
    val levels = listOf(
        Level("Nivel 1", "Granja", granja,  R.drawable.granja),
        Level("Nivel 2", "Océano", oceano, R.drawable.oceano),
        Level("Nivel 3", "Selva", selva, R.drawable.selva),
        Level("Nivel 4", "Sabana", sabana, R.drawable.sabana),
        Level("Nivel 5", "Pantano", pantano, R.drawable.pantano)
    )


    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        "Niveles",
                        color = Color.White,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
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
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueOne, // Color del fondo de la TopAppBar
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BlueOne, // Color de fondo del BottomAppBar
                actions = {
                    IconButton(onClick = { navController?.navigate("login_screen") }) {
                        Icon(Icons.Default.Home, contentDescription = "Inicio")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { navController?.navigate("preferences_screen")}) {
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
                    colors = androidx.compose.material3.CardDefaults.cardColors(
                        containerColor = level.color
                    ),
                    elevation = androidx.compose.material3.CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    onClick = { /* Acción al hacer clic en el nivel */ }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                        ,
                        contentAlignment = Alignment.TopStart,
                    ) {
                        Text(
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 20.sp,
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 5f,
                                    offset = androidx.compose.ui.geometry.Offset(0f, 5f)
                                )
                            ),
                            text = level.name, // Mostramos el nombre del nivel
                            color = Color.White,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,

                        )
                        Image(
                            painter = painterResource(id = level.imageResId),
                            contentDescription = "Imagen del nivel",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.BottomCenter)
                        )
                        Text(
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 20.sp,
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 5f,
                                    offset = androidx.compose.ui.geometry.Offset(0f, 5f)
                                )
                            ),
                            modifier = Modifier.align(Alignment.BottomEnd),
                            text = level.location, // Mostramos el nombre del nivel
                            color = Color.White,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenLevelsPreview() {
    LittleElephantTheme {
        SecondScreenLevels()
    }
}


