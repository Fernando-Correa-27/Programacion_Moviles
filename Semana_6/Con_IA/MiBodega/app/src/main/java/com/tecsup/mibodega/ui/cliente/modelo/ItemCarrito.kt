package com.tecsup.mibodega.ui.cliente.modelo

data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int
)

fun List<ItemCarrito>.cantidadTotalProductos(): Int =
    sumOf { it.cantidad.coerceAtLeast(0) }

