package com.correa.tecsup_store.ui.components

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.correa.tecsup_store.model.Producto

/**
 * Tarjeta de producto heredada del Laboratorio 4, adaptada al catalogo
 * de la TECSUP Store.
 */
@Composable
fun ProductCard(
    producto: Producto,
    modifier: Modifier = Modifier,
    menuExpandido: Boolean = false,
    onToggleMenu: () -> Unit = {},
    esFavorito: Boolean = false,
    onClick: () -> Unit = {},
    onAgregarAlCarrito: () -> Unit = {},
    onAlternarFavorito: () -> Unit = {},
    onEliminar: () -> Unit = {}
) {
    // El estado de expansion vive en la pantalla padre: asi el menu queda
    // asociado a un solo producto a la vez.
    fun cerrarMenu() = onToggleMenu()

    Box(modifier = modifier) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(56.dp)
            ) {
                Text(
                    text = producto.emoji,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stock: ${producto.cantidad} unidades",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "S/ ${"%.2f".format(producto.precio)}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onToggleMenu) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opciones de ${producto.nombre}"
                    )
                }
            }
        }
    }

    DropdownMenu(
        expanded = menuExpandido,
        onDismissRequest = { cerrarMenu() },
        shape = RoundedCornerShape(14.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        // Encabezado del menu: identifica a que producto pertenece
        Text(
            text = producto.nombre,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        DropdownMenuItem(
            text = { Text("Ver detalle") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = null
                )
            },
            onClick = {
                cerrarMenu()
                onClick()
            }
        )
        DropdownMenuItem(
            text = { Text("Agregar al carrito") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null
                )
            },
            onClick = {
                cerrarMenu()
                onAgregarAlCarrito()
            }
        )

        // Separador entre el bloque de acciones y el bloque de-edicion
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        DropdownMenuItem(
            text = { Text(if (esFavorito) "Quitar de favoritos" else "Marcar como favorito") },
            leadingIcon = {
                Icon(
                    imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (esFavorito) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            onClick = {
                cerrarMenu()
                onAlternarFavorito()
            }
        )
        DropdownMenuItem(
            text = { Text("Eliminar del carrito") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            onClick = {
                cerrarMenu()
                onEliminar()
            }
        )
    }
    }
}
