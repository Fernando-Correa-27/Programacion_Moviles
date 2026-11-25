package com.correa.centrotareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.correa.centrotareas.ui.PantallaTareas
import com.correa.centrotareas.ui.theme.CentrotareasTheme
import com.correa.centrotareas.viewmodel.TareasViewModel
import com.correa.centrotareas.viewmodel.TareasViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CentrotareasTheme {
                val viewModel: TareasViewModel = viewModel(factory = TareasViewModelFactory())
                PantallaTareas(viewModel = viewModel)
            }
        }
    }
}
