package com.correa.tecsup_store.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.correa.tecsup_store.data.TiendaEstado
import com.correa.tecsup_store.model.Producto
import com.correa.tecsup_store.ui.components.ProductCard

/**
 * Pantalla de carrito portada del Laboratorio 4: conserva el calculo de
 * subtotal, IGV y el descuento por tramos.
 */
@Composable
fun CartScreen(
    estado: TiendaEstado,
    modifier: Modifier = Modifier
) {
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }
    var productoConMenu by remember { mutableStateOf<Producto?>(null) }

    Column(modifier = modifier.fillMaxSize()) {

        if (estado.carrito.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Agrega productos desde el catálogo",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(estado.carrito, key = { it.id }) { producto ->
                    ProductCard(
                        producto = producto,
                        menuExpandido = productoConMenu?.id == producto.id,
                        onToggleMenu = {
                            productoConMenu = if (productoConMenu?.id == producto.id) null else producto
                        },
                        esFavorito = estado.esFavorito(producto.id),
                        onClick = { productoAEliminar = producto },
                        onAgregarAlCarrito = { estado.agregarAlCarrito(producto) },
                        onAlternarFavorito = { estado.alternarFavorito(producto.id) },
                        onEliminar = { estado.eliminarDelCarrito(producto) }
                    )
                }
            }
        }

        val subtotal = estado.totalCarrito
        val igv = subtotal * 0.18
        val total = subtotal + igv

        val descuento = when {
            total > 5000 -> total * 0.10
            total > 3000 -> total * 0.05
            else -> 0.0
        }
        val totalConDescuento = total - descuento

        Surface(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Productos: ${estado.carrito.size}")
                    Text("Unidades: ${estado.unidadesEnCarrito}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal")
                    Text("S/ ${"%.2f".format(subtotal)}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("IGV (18%)")
                    Text("S/ ${"%.2f".format(igv)}")
                }

                if (descuento > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Descuento")
                        Text(
                            "- S/ ${"%.2f".format(descuento)}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "TOTAL",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "S/ ${"%.2f".format(totalConDescuento)}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                OutlinedButton(
                    onClick = { estado.carrito.clear() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Vaciar carrito")
                }
            }
        }
    }

    productoAEliminar?.let { producto ->
        AlertDialog(
            onDismissRequest = { productoAEliminar = null },
            title = { Text("¿Eliminar del carrito?") },
            text = { Text(producto.nombre) },
            confirmButton = {
                TextButton(onClick = {
                    estado.eliminarDelCarrito(producto)
                    productoAEliminar = null
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { productoAEliminar = null }) { Text("Cancelar") }
            }
        )
    }
}
