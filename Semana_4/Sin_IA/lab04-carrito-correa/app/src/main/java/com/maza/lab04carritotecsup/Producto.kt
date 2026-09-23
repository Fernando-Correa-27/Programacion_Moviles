package com.maza.lab04carritotecsup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Producto(
    val nombre: String,
    val precio: Double,
    val cantidad: Int
)


val MoradoTecsup = Color(0xFF6A4FA3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Estructura() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Mi Carrito TECSUP",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MoradoTecsup
            )
        )
        PantallaCarrito()
    }
}


@Composable
fun PantallaCarrito() {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    val productos = remember { mutableStateListOf<Producto>() }

    // NUEVO (Reto 1): guarda el producto que se está por eliminar, mientras se confirma
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = {
                val precioNum = precio.toDoubleOrNull() ?: 0.0
                val cantidadNum = cantidad.toIntOrNull() ?: 0
                if (nombre.isNotBlank() && precioNum > 0 && cantidadNum > 0) {
                    productos.add(Producto(nombre, precioNum, cantidadNum))
                    nombre = ""
                    precio = ""
                    cantidad = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("AGREGAR") }

        Text("Productos: ${productos.size}")

        if (productos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Tu carrito está vacío", color = Color.Gray, style = MaterialTheme.typography.titleMedium)
                    Text("Agrega tu primer producto", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(productos) { producto ->
                    TarjetaProducto(
                        producto = producto,
                        // CAMBIO (Reto 1): ya no elimina directo, solo marca cuál se quiere eliminar
                        onEliminar = { productoAEliminar = producto }
                    )
                }
            }
        }


        val subtotal = productos.sumOf { it.precio * it.cantidad }
        val igv = subtotal * 0.18
        val total = subtotal + igv

        // NUEVO (Reto 2): descuento según el total
        val descuento = when {
            total > 5000 -> total * 0.10
            total > 3000 -> total * 0.05
            else -> 0.0
        }
        val totalConDescuento = total - descuento

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Productos: ${productos.size}")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Subtotal")
                    Text("S/ ${"%.2f".format(subtotal)}")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("IGV (18%)")
                    Text("S/ ${"%.2f".format(igv)}")
                }

                // NUEVO (Reto 2): solo se muestra si hay descuento aplicable
                if (descuento > 0) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Descuento")
                        Text(
                            "- S/ ${"%.2f".format(descuento)}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("TOTAL", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(
                        // CAMBIO (Reto 2): usa el total con descuento aplicado
                        "S/ ${"%.2f".format(totalConDescuento)}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // NUEVO (Reto 1): diálogo de confirmación, se muestra solo si hay un producto marcado
        productoAEliminar?.let { p ->
            AlertDialog(
                onDismissRequest = { productoAEliminar = null },
                title = { Text("¿Eliminar este producto?") },
                text = { Text(p.nombre) },
                confirmButton = {
                    TextButton(onClick = {
                        productos.remove(p)
                        productoAEliminar = null
                    }) { Text("Eliminar") }
                },
                dismissButton = {
                    TextButton(onClick = { productoAEliminar = null }) { Text("Cancelar") }
                }
            )
        }
    }

}

@Composable
fun TarjetaProducto(producto: Producto, onEliminar: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "S/ ${"%.2f".format(producto.precio)} x ${producto.cantidad}",
                    color = Color.Gray
                )
            }
            Text(
                text = "S/ ${"%.2f".format(producto.precio * producto.cantidad)}",
                fontWeight = FontWeight.SemiBold
            )
            IconButton(onClick = onEliminar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}