package com.tecsup.mibodega.ui.cliente.modelo

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val imagenRes: Int
)

enum class OrdenPrecio {
    RECOMENDADOS,
    MENOR_A_MAYOR,
    MAYOR_A_MENOR
}

fun ordenarPorPrecio(productos: List<Producto>, orden: OrdenPrecio): List<Producto> = when (orden) {
    OrdenPrecio.RECOMENDADOS -> productos.toList()
    OrdenPrecio.MENOR_A_MAYOR -> productos.sortedBy { it.precio }
    OrdenPrecio.MAYOR_A_MENOR -> productos.sortedByDescending { it.precio }
}

