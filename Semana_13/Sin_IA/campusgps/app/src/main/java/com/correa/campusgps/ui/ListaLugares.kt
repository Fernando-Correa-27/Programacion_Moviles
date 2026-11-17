package com.correa.campusgps.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.correa.campusgps.model.LugarConDistancia
import com.correa.campusgps.util.formatearDistancia

@Composable
fun ListaLugares(
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val estadoLista = rememberLazyListState()
    LaunchedEffect(lugares) { estadoLista.scrollToItem(0) }
    LazyColumn(
        modifier = modifier,
        state = estadoLista,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(lugares, key = { it.lugar.id }) { conDistancia ->
            val seleccionadoActual = conDistancia.lugar.id == seleccionado
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (seleccionadoActual) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.surface
                ),
                border = if (seleccionadoActual) {
                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                } else {
                    null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .semantics { selected = seleccionadoActual }
                    .clickable { onClick(conDistancia.lugar.id) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, androidx.compose.foundation.shape.CircleShape)
                            .padding(5.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(conDistancia.lugar.nombre, fontWeight = FontWeight.Bold)
                        Text(conDistancia.lugar.categoria, style = MaterialTheme.typography.bodySmall)
                    }
                    Text(conDistancia.metros?.let(::formatearDistancia) ?: "Sin calcular")
                }
            }
        }
    }
}
