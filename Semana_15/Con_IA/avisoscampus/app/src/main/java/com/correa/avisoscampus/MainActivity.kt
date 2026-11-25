package com.correa.avisoscampus

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.correa.avisoscampus.ui.PantallaAvisos
import com.correa.avisoscampus.ui.theme.AvisoscampusTheme
import com.correa.avisoscampus.viewmodel.AvisosViewModel
import com.correa.avisoscampus.viewmodel.avisosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        val factory = avisosViewModelFactory((application as AvisosApp).repositorio)
        setContent {
            AvisoscampusTheme(dynamicColor = false) {
                val vm: AvisosViewModel = viewModel(factory = factory)
                PantallaAvisos(vm)
            }
        }
    }
}
