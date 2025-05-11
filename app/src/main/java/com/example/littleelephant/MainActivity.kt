package com.example.littleelephant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.littleelephant.naviagtion.AppNavigation
import com.example.littleelephant.ui.theme.LittleElephantTheme

/**
 * MainActivity es la actividad principal de la aplicación.
 * Es el punto de entrada donde se inicializa el tema y la navegación principal.
 */
class MainActivity : ComponentActivity() {

    /**
     * Método onCreate llamado cuando la actividad es creada.
     * Se utiliza para configurar el contenido inicial de la actividad.
     *
     * @param savedInstanceState Estado previamente guardado, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita el modo de pantalla completa, permitiendo que el contenido ocupe toda la pantalla
        enableEdgeToEdge()

        // Establece el contenido principal de la actividad
        setContent {
            LittleElephantTheme {
                Surface {
                    // Navegación principal de la aplicación
                    AppNavigation()
                }
            }
        }
    }
}

/**
 * Función de vista previa para mostrar el contenido de la actividad en el editor de diseño.
 */
@Preview
@Composable
fun DefaultPreview() {
    LittleElephantTheme {
        AppNavigation()
    }
}
