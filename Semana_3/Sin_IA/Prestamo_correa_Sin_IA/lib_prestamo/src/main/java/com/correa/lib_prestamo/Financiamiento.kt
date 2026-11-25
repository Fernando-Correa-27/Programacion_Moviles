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

    fun mostrarResumen() {
        println("\nMonto inicial: ${"%.2f".format(producto.montoTotal())}")
        println("Interes (${(interes * 100).toInt()}%): ${"%.2f".format(montoInteres())}")
        println("Monto a pagar: ${"%.2f".format(montoAPagar())}")
        println("Pago mensual: ${"%.2f".format(pagoMensual())}")
    }

    fun mostrarCronograma() {
        var saldo = montoAPagar()
        var fecha = LocalDate.now()

        println("\n%-4s%-12s%-14s%-14s%-14s".format("N°", "Fecha", "Monto", "P.Mensual", "Resta Pago"))
        for (i in 1..numCuotas) {
            val saldoAntes = saldo
            saldo -= pagoMensual()

            if (Math.abs(saldo) < 0.01) saldo = 0.0

            println(
                "%-4d%-12s%-14s%-14s%-14s".format(
                    i,
                    fecha.toString(),
                    "%.2f".format(saldoAntes),
                    "%.2f".format(pagoMensual()),
                    "%.2f".format(saldo)
                )
            )
            fecha = fecha.plusMonths(1)
        }
    }
}