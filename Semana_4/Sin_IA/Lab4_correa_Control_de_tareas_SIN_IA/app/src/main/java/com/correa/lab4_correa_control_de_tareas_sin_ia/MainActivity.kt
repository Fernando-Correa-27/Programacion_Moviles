package com.correa.lab4_correa_control_de_tareas_sin_ia

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.correa.lab4_correa_control_de_tareas_sin_ia.ui.theme.Lab4_correa_Control_de_tareas_SIN_IATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4_correa_Control_de_tareas_SIN_IATheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TemperatureDisplay(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TemperatureDisplay(modifier: Modifier = Modifier) {
    var temperatura by remember { mutableIntStateOf(20) }

    val colorTexto = when {
        temperatura > 30 -> Color.Red
        temperatura < 10 -> Color.Blue
        else -> Color.Unspecified
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Temperatura: $temperatura°",
            color = colorTexto
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { temperatura++ }) {
                Text("Subir")
            }
            Button(onClick = { temperatura-- }) {
                Text("Bajar")
            }
            Button(onClick = { temperatura = 20 }) {
                Text("Resetear")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4_correa_Control_de_tareas_SIN_IATheme {

    }
}