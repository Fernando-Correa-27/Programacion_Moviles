package com.correa.registro_de_notas_sin_ia.uii

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {

    CenterAlignedTopAppBar(
        title = {
            Text("Registro de Notas")
        }
    )
}