package com.tecsup.mibodega

import com.tecsup.mibodega.ui.cliente.modelo.camposRequeridosCompletos
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidacionCamposTest {
    @Test
    fun aceptaCamposConContenido() {
        assertTrue(camposRequeridosCompletos("Juan", "987654321"))
    }

    @Test
    fun rechazaCamposVaciosOConEspacios() {
        assertFalse(camposRequeridosCompletos("Juan", " "))
        assertFalse(camposRequeridosCompletos(""))
    }
}
