package com.correa.registro_de_notas_sin_ia.uii

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {

    TopAppBar(
        title = {
            Text("Registro de Notas")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PurpuraPrincipal,
            titleContentColor = Color.White
        )
    )
}