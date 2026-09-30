package com.correa.tecsup_store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.correa.tecsup_store.data.TiendaEstado
import com.correa.tecsup_store.navigation.AppNavigation
import com.correa.tecsup_store.ui.theme.TECSUP_StoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TECSUP_StoreTheme {
                val estado = remember { TiendaEstado() }
                AppNavigation(estado = estado)
            }
        }
    }
}
