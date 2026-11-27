package com.tecsup.rickandmorty.ui.lista

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.tecsup.rickandmorty.domain.model.Item
import com.tecsup.rickandmorty.ui.UiState
import com.tecsup.rickandmorty.ui.components.Cargando
import com.tecsup.rickandmorty.ui.components.ErrorView
import com.tecsup.rickandmorty.ui.components.ItemCard

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ListaScreen(
    estado: UiState<List<Item>>, avisoSinConexion: String?, esAdmin: Boolean,
    alReintentar: () -> Unit, alDetalle: (Int) -> Unit, alFavorito: (Int) -> Unit,
    alFavoritos: () -> Unit, alAdmin: () -> Unit, alCerrarSesion: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Personajes") }, actions = {
            TextButton(onClick = alFavoritos) { Text("Favoritos") }
            if (esAdmin) TextButton(onClick = alAdmin) { Text("Admin") }
            TextButton(onClick = alCerrarSesion) { Text("Salir") }
        })
    }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            avisoSinConexion?.let {
                Text(it, Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp).semantics { liveRegion = LiveRegionMode.Polite }, color = MaterialTheme.colorScheme.error)
            }
            when (estado) {
                UiState.Cargando -> Cargando(Modifier.padding(20.dp))
                is UiState.Error -> ErrorView(estado.mensaje, alReintentar)
                is UiState.Exito -> if (estado.datos.isEmpty()) {
                    Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("No hay personajes guardados. Sincroniza cuando tengas conexión.")
                        Button(onClick = alReintentar) { Text("Sincronizar") }
                    }
                } else LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(estado.datos, key = { it.id }) { item ->
                        ItemCard(item, { alDetalle(item.id) }, { alFavorito(item.id) })
                    }
                }
            }
        }
    }
}
