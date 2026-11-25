package com.correa.avisoscampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.correa.avisoscampus.model.Aviso

@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    TarjetaAvisos("Historial") {
        if (avisos.isEmpty()) Text("Sin avisos todavía")
        avisos.take(20).forEach { aviso ->
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    aviso.origen.texto,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(aviso.hora, style = MaterialTheme.typography.labelMedium)
            }
            Text(aviso.titulo, style = MaterialTheme.typography.titleSmall)
            Text(aviso.texto, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
