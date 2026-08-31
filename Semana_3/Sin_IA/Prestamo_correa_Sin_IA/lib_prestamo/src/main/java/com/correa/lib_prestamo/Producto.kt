package com.correa.lib_prestamo

class Producto(
    val nombre: String,
    val precio: Double,
    val cantidad: Int) {
    fun montoTotal(): Double = precio * cantidad
}