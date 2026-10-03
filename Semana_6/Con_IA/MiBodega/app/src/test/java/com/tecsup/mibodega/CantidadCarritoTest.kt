package com.tecsup.mibodega

import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.cantidadTotalProductos
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import org.junit.Assert.assertEquals
import org.junit.Test

class CantidadCarritoTest {
    @Test
    fun cuentaTodasLasUnidadesDelCarrito() {
        val carrito = listOf(ItemCarrito(listaProductosFake[0], 2), ItemCarrito(listaProductosFake[1], 3))

        assertEquals(5, carrito.cantidadTotalProductos())
    }

    @Test
    fun carritoVacioNoMuestraUnidades() {
        assertEquals(0, emptyList<ItemCarrito>().cantidadTotalProductos())
    }
}
