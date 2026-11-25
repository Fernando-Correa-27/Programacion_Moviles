package com.correa.lab4_correa_control_de_tareas_sin_ia.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.correa.lab4_correa_control_de_tareas_sin_ia.Tarea

@Composable
fun TareaItem(
    tarea: Tarea,
    onToggleCompletada: (Int) -> Unit,
    onEliminar: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = { onToggleCompletada(tarea.id) }
            )

            Text(
                text = tarea.texto,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                textDecoration = if (tarea.completada) TextDecoration.LineThrough else TextDecoration.None
            )

            IconButton(onClick = { onEliminar(tarea.id) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar tarea")
            }
        }
    }
}