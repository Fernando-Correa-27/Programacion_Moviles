package com.tecsup.mibodega

import com.tecsup.mibodega.ui.cliente.modelo.ModalidadEntrega
import com.tecsup.mibodega.ui.cliente.modelo.calcularTotalPedido
import org.junit.Assert.assertEquals
import org.junit.Test

class ModalidadEntregaTest {
    @Test
    fun deliveryAgregaElCostoConfigurado() {
        assertEquals(14.0, calcularTotalPedido(10.0, ModalidadEntrega.DELIVERY), 0.0)
    }

    @Test
    fun recojoNoAgregaCostoDeEnvio() {
        assertEquals(10.0, calcularTotalPedido(10.0, ModalidadEntrega.RECOJO_EN_TIENDA), 0.0)
    }
}
