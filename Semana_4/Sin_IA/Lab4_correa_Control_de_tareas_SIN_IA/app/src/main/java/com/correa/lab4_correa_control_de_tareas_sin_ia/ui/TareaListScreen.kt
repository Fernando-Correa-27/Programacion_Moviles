package com.correa.lab4_correa_control_de_tareas_sin_ia.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.correa.lab4_correa_control_de_tareas_sin_ia.Tarea
import androidx.compose.ui.tooling.preview.Preview
import com.correa.lab4_correa_control_de_tareas_sin_ia.ui.theme.Lab4_correa_Control_de_tareas_SIN_IATheme

@Composable
fun TareaListScreen(modifier: Modifier = Modifier) {
    var textoTarea by remember { mutableStateOf("") }
    val tareas = remember { mutableStateListOf<Tarea>() }
    var nextId by remember { mutableStateOf(1) }

    Column(modifier = modifier.padding(16.dp)) {

        Text(text = "Tareas: ${tareas.size}")

        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            OutlinedTextField(
                value = textoTarea,
                onValueChange = { textoTarea = it },
                modifier = Modifier.weight(1f),
                label = { Text("Nueva tarea") }
            )

            Button(
                onClick = {
                    if (textoTarea.isNotBlank()) {
                        tareas.add(Tarea(id = nextId, texto = textoTarea))
                        nextId++
                        textoTarea = ""
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Agregar")
            }
        }

        LazyColumn {
            items(tareas, key = { it.id }) { tarea ->
                TareaItem(
                    tarea = tarea,
                    onToggleCompletada = { id ->
                        val index = tareas.indexOfFirst { it.id == id }
                        if (index != -1) {
                            tareas[index] =
                                tareas[index].copy(completada = !tareas[index].completada)
                        }
                    },
                    onEliminar = { id ->
                        tareas.removeAll { it.id == id }
                    },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareaListScreenPreview() {
    Lab4_correa_Control_de_tareas_SIN_IATheme {
        TareaListScreen()
    }
}