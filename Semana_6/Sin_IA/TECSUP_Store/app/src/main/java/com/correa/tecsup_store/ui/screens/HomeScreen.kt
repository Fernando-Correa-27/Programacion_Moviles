package com.correa.tecsup_store.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.correa.tecsup_store.data.TiendaEstado
import com.correa.tecsup_store.model.CatalogoTienda
import com.correa.tecsup_store.model.Producto
import com.correa.tecsup_store.ui.components.ProductCard

@Composable
fun HomeScreen(
    estado: TiendaEstado,
    onVerDetalle: (Producto) -> Unit,
    modifier: Modifier = Modifier
) {
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    val productosFiltrados = remember(categoriaSeleccionada) {
        if (categoriaSeleccionada == "Todos") {
            CatalogoTienda.productos
        } else {
            CatalogoTienda.productos.filter { it.categoria == categoriaSeleccionada }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {

        // LazyRow de categorias
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(CatalogoTienda.categorias) { categoria ->
                val seleccionada = categoria.nombre == categoriaSeleccionada
                FilterChip(
                    selected = seleccionada,
                    onClick = { categoriaSeleccionada = categoria.nombre },
                    label = { Text(categoria.nombre) },
                    leadingIcon = {
                        Icon(
                            imageVector = categoria.icono,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        // LazyColumn con las secciones de productos
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                BannerTienda(unidades = estado.unidadesEnCarrito)
            }

            if (productosFiltrados.isEmpty()) {
                item {
                    Text(
                        text = "No hay productos para esta categoría",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                item {
                    Text(
                        text = if (categoriaSeleccionada == "Todos") {
                            "Todos los productos"
                        } else {
                            "Categoría: $categoriaSeleccionada"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(productosFiltrados, key = { it.id }) { producto ->
                    ProductCard(
                        producto = producto,
                        onClick = { onVerDetalle(producto) }
                    )
                }
            }

            CatalogoTienda.secciones.forEach { seccion ->
                item(key = "titulo-${seccion.titulo}") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = seccion.titulo,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                items(seccion.productos, key = { "${seccion.titulo}-${it.id}" }) { producto ->
                    ProductCard(
                        producto = producto,
                        onClick = { onVerDetalle(producto) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BannerTienda(unidades: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "TECSUP Store",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tecnología, accesorios y uniques para la comunidad TECSUP",
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "$unidades",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
