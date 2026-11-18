package com.correa.campusgps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Fila de chips para filtrar los lugares por categoría.
 * La categoría seleccionada vive en el ViewModel; aquí solo se dibuja y se notifica.
 */
@Composable
fun FiltroCategorias(
    categorias: List<String>,
    seleccionada: String?,
    onSeleccionar: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val colores = FilterChipDefaults.filterChipColors(
        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 1.dp)
    ) {
        item {
            ChipCategoria(
                texto = "Todas",
                activo = seleccionada == null,
                colores = colores,
                onClick = { onSeleccionar(null) }
            )
        }
        items(categorias) { categoria ->
            ChipCategoria(
                texto = categoria,
                activo = seleccionada == categoria,
                colores = colores,
                onClick = { onSeleccionar(categoria) }
            )
        }
    }
}

/** Chip con color de texto explícito para que el contraste se mantenga en claro y oscuro. */
@Composable
private fun ChipCategoria(
    texto: String,
    activo: Boolean,
    colores: SelectableChipColors,
    onClick: () -> Unit
) {
    FilterChip(
        selected = activo,
        onClick = onClick,
        label = {
            Text(
                texto,
                style = MaterialTheme.typography.labelLarge,
                color = if (activo) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        colors = colores
    )
}
