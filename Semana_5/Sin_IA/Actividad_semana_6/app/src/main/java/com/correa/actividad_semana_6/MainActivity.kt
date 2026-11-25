package com.correa.actividad_semana_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.correa.actividad_semana_6.ui.theme.Actividad_semana_6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Actividad_semana_6Theme {
                ClinicaApp()
            }
        }
    }
}