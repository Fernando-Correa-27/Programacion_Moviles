package com.tecsup.rickandmorty.ui.favoritos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.rickandmorty.domain.model.Item
import com.tecsup.rickandmorty.ui.UiState
import com.tecsup.rickandmorty.ui.components.Cargando
import com.tecsup.rickandmorty.ui.components.ItemCard

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FavoritosScreen(estado: UiState<List<Item>>, alDetalle: (Int) -> Unit, alFavorito: (Int) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Mis favoritos") }) }) { padding ->
        when (estado) {
            UiState.Cargando -> Cargando(Modifier.padding(padding))
            is UiState.Error -> Text(estado.mensaje, Modifier.padding(padding).padding(24.dp))
            is UiState.Exito -> if (estado.datos.isEmpty()) Text("Aún no tienes favoritos", Modifier.padding(padding).padding(24.dp))
            else LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(estado.datos, key = { it.id }) { item -> ItemCard(item, { alDetalle(item.id) }, { alFavorito(item.id) }) }
            }
        }
    }
}
