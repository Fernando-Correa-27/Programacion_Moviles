package com.correa.tecsup_store.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.correa.tecsup_store.data.TiendaEstado
import com.correa.tecsup_store.model.CatalogoTienda
import com.correa.tecsup_store.model.Producto
import com.correa.tecsup_store.ui.components.ProductCard

@Composable
fun FavoritesScreen(
    estado: TiendaEstado,
    onVerDetalle: (Producto) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoritos = CatalogoTienda.productos.filter { estado.esFavorito(it.id) }

    if (favoritos.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Todavía no tienes favoritos",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Marca productos con el menú contextual ⋮",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Tus productos favoritos",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
        items(favoritos, key = { it.id }) { producto ->
            ProductCard(
                producto = producto,
                onClick = { onVerDetalle(producto) }
            )
        }
    }
}
