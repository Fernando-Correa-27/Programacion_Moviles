package com.tecsup.rickandmorty.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tecsup.rickandmorty.domain.model.Item

@Composable
fun ItemCard(item: Item, alAbrir: () -> Unit, alAlternarFavorito: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = alAbrir), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = item.imagenUrl,
                contentDescription = "Imagen de ${item.titulo}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(92.dp).clip(RoundedCornerShape(14.dp))
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(item.titulo, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(item.subtitulo, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                Text("Vivo", color = Color(0xFF16834A), style = MaterialTheme.typography.labelMedium)
            }
            IconButton(onClick = alAlternarFavorito, modifier = Modifier.semantics {
                contentDescription = if (item.esFavorito) "Quitar ${item.titulo} de favoritos" else "Agregar ${item.titulo} a favoritos"
            }) {
                Icon(
                    imageVector = if (item.esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (item.esFavorito) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
