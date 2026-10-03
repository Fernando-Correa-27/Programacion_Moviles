package com.tecsup.mibodega.ui.cliente.modelo

data class Pedido(
    val id: Int,
    val productos: List<ItemCarrito>,
    val total: Double,
    val direccion: String,
    val modalidad: ModalidadEntrega = ModalidadEntrega.DELIVERY,
    val fechaMillis: Long = System.currentTimeMillis()
)
