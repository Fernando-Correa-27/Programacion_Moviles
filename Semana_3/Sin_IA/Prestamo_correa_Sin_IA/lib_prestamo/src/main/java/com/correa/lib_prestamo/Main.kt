package com.correa.lib_prestamo

fun main() {
    print("Nombre del producto: ")
    val nombre = readLine()!!
    print("Precio: ")
    val precio = readLine()!!.toDouble()
    print("Cantidad: ")
    val cantidad = readLine()!!.toInt()

    val producto = Producto(nombre, precio, cantidad)

    print("Número de cuotas (6, 12, 24): ")
    val numCuotas = readLine()!!.toInt()

    val financiamiento = Financiamiento(producto, numCuotas)
    financiamiento.mostrarResumen()
    financiamiento.mostrarCronograma()
}