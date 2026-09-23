package com.correa.tecsupfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.correa.tecsupfit.ui.theme.TecsupFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TecsupFitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Saludo(
                        nombre = "Diego",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Saludo(nombre: String, modifier: Modifier = Modifier) {
    MaterialTheme {
        Text(
            text = "TECSUP Fit - Hola $nombre",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SaludoPreview() {
    TecsupFitTheme {
        Saludo("Diego")
    }
}