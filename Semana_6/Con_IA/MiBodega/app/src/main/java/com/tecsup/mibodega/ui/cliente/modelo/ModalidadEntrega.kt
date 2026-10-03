package com.tecsup.mibodega.ui.cliente.modelo

enum class ModalidadEntrega {
    DELIVERY,
    RECOJO_EN_TIENDA
}

const val COSTO_DELIVERY = 4.00

fun costoEntrega(modalidad: ModalidadEntrega): Double =
    if (modalidad == ModalidadEntrega.DELIVERY) COSTO_DELIVERY else 0.0

fun calcularTotalPedido(subtotal: Double, modalidad: ModalidadEntrega): Double =
    subtotal + costoEntrega(modalidad)
