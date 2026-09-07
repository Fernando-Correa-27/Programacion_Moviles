package com.correa.registro_de_notas_sin_ia.uii

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CursoItem(
    nombre: String,
    peso: Float
) {
    Text(
        text = "$nombre (${(peso * 100).toInt()}%)"
    )
}