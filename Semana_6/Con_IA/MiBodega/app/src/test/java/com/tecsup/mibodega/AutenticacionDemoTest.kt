package com.tecsup.mibodega

import com.tecsup.mibodega.ui.cliente.modelo.credencialesDemoValidas
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AutenticacionDemoTest {
    @Test
    fun aceptaCredencialesDemoCorrectas() {
        assertTrue(credencialesDemoValidas("cliente", "1234"))
        assertTrue(credencialesDemoValidas(" cliente ", "1234"))
    }

    @Test
    fun rechazaCredencialesIncorrectasOVacias() {
        assertFalse(credencialesDemoValidas("cliente", "0000"))
        assertFalse(credencialesDemoValidas("", "1234"))
        assertFalse(credencialesDemoValidas("cliente", ""))
    }
}
