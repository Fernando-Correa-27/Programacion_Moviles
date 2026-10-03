package com.tecsup.mibodega.ui.cliente.screens.categorias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.componentes.BarraNavegacionCliente
import com.tecsup.mibodega.ui.cliente.componentes.SeccionCliente
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaCategorias
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.theme.GrisClaro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriasScreen(
    productos: List<Producto> = listaProductosFake,
    onCategoriaSeleccionada: (String) -> Unit,
    onSeccionSeleccionada: (SeccionCliente) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Categorías") }) },
        bottomBar = {
            BarraNavegacionCliente(SeccionCliente.CATEGORIAS, onSeccionSeleccionada)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listaCategorias.forEach { categoria ->
                FilaCategoria(
                    categoria = categoria,
                    cantidad = if (categoria == "Todos") productos.size else productos.count { it.categoria == categoria },
                    icono = iconoCategoria(categoria),
                    onClick = { onCategoriaSeleccionada(categoria) }
                )
            }
        }
    }
}

@Composable
private fun FilaCategoria(
    categoria: String,
    cantidad: Int,
    icono: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = GrisClaro)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(categoria, modifier = Modifier.weight(1f), fontWeight = FontWeight.SemiBold)
            Text(
                "$cantidad ${if (cantidad == 1) "producto" else "productos"}",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun iconoCategoria(categoria: String): ImageVector = when (categoria) {
    "Bebidas" -> Icons.Default.LocalDrink
    "Abarrotes" -> Icons.Default.Inventory2
    "Snacks" -> Icons.Default.Cookie
    else -> Icons.Default.ShoppingCart
}
