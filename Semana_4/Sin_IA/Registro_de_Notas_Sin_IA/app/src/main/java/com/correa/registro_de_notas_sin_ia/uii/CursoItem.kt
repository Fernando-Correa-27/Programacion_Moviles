package com.correa.registro_de_notas_sin_ia.uii

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CursoItem(
    nombre: String,
    peso: Float,
    nota: Float,
    onNotaChange: (Float) -> Unit
) {
    Text(
        text = "$nombre (${(peso * 100).toInt()}%)"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Slider(
            value = nota,
            onValueChange = onNotaChange,
            valueRange = 0f..20f,
            steps = 19,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = nota.toInt().toString()
        )
    }
}