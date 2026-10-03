package com.tecsup.mibodega

import com.tecsup.mibodega.ui.cliente.modelo.OrdenPrecio
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.modelo.ordenarPorPrecio
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Test

class OrdenPrecioTest {
    @Test
    fun ordenaDeMenorAMayorSinAlterarElCatalogo() {
        val ordenados = ordenarPorPrecio(listaProductosFake, OrdenPrecio.MENOR_A_MAYOR)

        assertEquals(listOf(3.50, 4.50, 5.20, 6.50, 8.90), ordenados.map { it.precio })
        assertEquals(4.50, listaProductosFake[0].precio, 0.0)
        assertNotSame(listaProductosFake, ordenados)
    }

    @Test
    fun ordenaDeMayorAMenor() {
        val ordenados = ordenarPorPrecio(listaProductosFake, OrdenPrecio.MAYOR_A_MENOR)

        assertEquals(listOf(8.90, 6.50, 5.20, 4.50, 3.50), ordenados.map { it.precio })
    }
}
