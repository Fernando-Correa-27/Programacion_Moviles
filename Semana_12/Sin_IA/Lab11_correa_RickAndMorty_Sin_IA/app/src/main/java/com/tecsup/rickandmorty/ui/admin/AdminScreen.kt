package com.tecsup.rickandmorty.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.rickandmorty.ui.UiState
import com.tecsup.rickandmorty.ui.components.Cargando

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AdminScreen(estado: UiState<Totales>, alSincronizar: () -> Unit, alLimpiarCache: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Panel de administración") }) }) { padding ->
        Column(Modifier.padding(padding).padding(24.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
            when (estado) {
                UiState.Cargando -> Cargando()
                is UiState.Error -> Text(estado.mensaje)
                is UiState.Exito -> {
                    Text("Personajes guardados: ${estado.datos.guardados}")
                    Text("Favoritos: ${estado.datos.favoritos}")
                }
            }
            Button(onClick = alSincronizar, Modifier.fillMaxWidth()) { Text("Sincronizar") }
            OutlinedButton(onClick = alLimpiarCache, Modifier.fillMaxWidth()) { Text("Limpiar caché") }
        }
    }
}
