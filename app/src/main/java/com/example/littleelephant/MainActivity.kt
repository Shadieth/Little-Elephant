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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleElephantTheme {
              Surface {
                 AppNavigation()
              }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    LittleElephantTheme {
        AppNavigation()
    }
}
