package com.correa.tecsup_store.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.correa.tecsup_store.model.Producto

/**
 * Estado compartido de la tienda. Reúne la lógica de carrito y favoritos que
 * venía suelta dentro de la PantallaCarrito del Laboratorio 4, pero ahora
 * vive por encima de la navegación para poder ser consumido desde el catálogo,
 * el carrito y el NavigationDrawer.
 */
class TiendaEstado {

    val carrito = mutableStateListOf<Producto>()
    val favoritos = mutableStateListOf<Int>()

    var mensaje by mutableStateOf<String?>(null)
        private set

    val unidadesEnCarrito: Int
        get() = carrito.sumOf { it.cantidad }

    val totalCarrito: Double
        get() = carrito.sumOf { it.precio * it.cantidad }

    fun agregarAlCarrito(producto: Producto) {
        val existente = carrito.firstOrNull { it.id == producto.id }
        if (existente == null) {
            carrito.add(producto.copy(cantidad = 1))
        } else {
            carrito[carrito.indexOf(existente)] = existente.copy(cantidad = existente.cantidad + 1)
        }
        mensaje = "«${producto.nombre}» agregado al carrito"
    }

    fun quitarDelCarrito(producto: Producto) {
        val existente = carrito.firstOrNull { it.id == producto.id } ?: return
        if (existente.cantidad > 1) {
            carrito[carrito.indexOf(existente)] = existente.copy(cantidad = existente.cantidad - 1)
        } else {
            carrito.remove(existente)
        }
        mensaje = "Se quitó una unidad de «${producto.nombre}»"
    }

    fun eliminarDelCarrito(producto: Producto) {
        carrito.removeAll { it.id == producto.id }
        mensaje = "«${producto.nombre}» eliminado del carrito"
    }

    fun alternarFavorito(id: Int) {
        if (favoritos.remove(id)) {
            mensaje = "Quitado de favoritos"
        } else {
            favoritos.add(id)
            mensaje = "Agregado a favoritos"
        }
    }

    fun esFavorito(id: Int): Boolean = favoritos.contains(id)

    fun limpiarMensaje() {
        mensaje = null
    }
}
