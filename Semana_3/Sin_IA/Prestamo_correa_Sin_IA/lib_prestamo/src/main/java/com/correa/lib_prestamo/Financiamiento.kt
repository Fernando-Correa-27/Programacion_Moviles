package com.correa.lib_prestamo

import java.time.LocalDate

class Financiamiento(
    val producto: Producto,
    val numCuotas: Int) {

    private val interes: Double = when (numCuotas) {
        6 -> 0.20
        12 -> 0.40
        24 -> 0.60
        else -> 0.0
    }

    fun montoInteres(): Double = producto.montoTotal() * interes
    fun montoAPagar(): Double = producto.montoTotal() + montoInteres()
    fun pagoMensual(): Double = montoAPagar() / numCuotas
}