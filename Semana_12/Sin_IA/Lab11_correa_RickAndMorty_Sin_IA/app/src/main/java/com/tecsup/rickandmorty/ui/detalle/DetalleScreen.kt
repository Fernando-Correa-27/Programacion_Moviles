package com.tecsup.rickandmorty.ui.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tecsup.rickandmorty.domain.model.Item
import com.tecsup.rickandmorty.ui.UiState
import com.tecsup.rickandmorty.ui.components.Cargando
import com.tecsup.rickandmorty.ui.components.ErrorView

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetalleScreen(estado: UiState<Item>, alReintentar: () -> Unit, alFavorito: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Detalle") }) }) { padding ->
        when (estado) {
            UiState.Cargando -> Cargando(Modifier.padding(padding))
            is UiState.Error -> ErrorView(estado.mensaje, alReintentar, Modifier.padding(padding))
            is UiState.Exito -> Column(
                Modifier.padding(padding).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AsyncImage(estado.datos.imagenUrl, "Imagen de ${estado.datos.titulo}", Modifier.fillMaxWidth().size(280.dp), contentScale = ContentScale.Crop)
                Text(estado.datos.titulo, style = MaterialTheme.typography.headlineMedium)
                Text(estado.datos.subtitulo)
                Text("Género: ${estado.datos.genero.orEmpty()}")
                Text("Origen: ${estado.datos.origen.orEmpty()}")
                Text("Ubicación: ${estado.datos.ubicacion.orEmpty()}")
                Text("Episodios: ${estado.datos.cantidadEpisodios ?: 0}")
                Button(onClick = alFavorito, shape = RoundedCornerShape(14.dp)) { Text(if (estado.datos.esFavorito) "Quitar de favoritos" else "Agregar a favoritos") }
            }
        }
    }
}
