package com.correa.lab02carritokotlin_ia
import kotlin.system.exitProcess

/**
 * Representa un producto dentro del carrito.
 * La lógica de cálculo de su propio importe vive dentro de la clase (encapsulamiento).
 */
class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
) {
    val importe: Double
        get() = precio * cantidad

    override fun toString(): String =
        String.format("%-20s x%d  S/ %8.2f", nombre, cantidad, importe)
}

/**
 * Representa al cliente que realiza la compra.
 */
class Cliente(val nombre: String)

/**
 * Encapsula toda la lógica de negocio del carrito: agregar, eliminar,
 * buscar productos y calcular subtotal, IGV, descuento y total.
 */
class Carrito(private val cliente: Cliente) {

    private val productos = mutableListOf<Producto>()
    private val igvPorcentaje = 0.18

    val cantidadProductos: Int
        get() = productos.size

    fun agregarProducto(producto: Producto) {
        productos.add(producto)
        println("Producto agregado: ${producto.nombre}")
    }

    fun eliminarProducto(nombre: String): Boolean {
        val eliminado = productos.removeIf { it.nombre.equals(nombre, ignoreCase = true) }
        if (eliminado) println("Producto eliminado: $nombre")
        else println("No se encontró '$nombre' en el carrito.")
        return eliminado
    }

    fun buscarProducto(nombre: String): Producto? =
        productos.find { it.nombre.equals(nombre, ignoreCase = true) }

    fun productoMasCaro(): Producto? = productos.maxByOrNull { it.precio }

    fun calcularSubtotal(): Double = productos.sumOf { it.importe }

    fun calcularIGV(subtotal: Double): Double = subtotal * igvPorcentaje

    fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv

    fun calcularDescuento(total: Double): Double = when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }

    fun descripcionDescuento(total: Double): String = when {
        total > 5000 -> "Descuento aplicado: 10% por compra mayor a S/ 5000"
        total > 3000 -> "Descuento aplicado: 5% por compra mayor a S/ 3000"
        else -> "Sin descuento aplicado (el total no supera S/ 3000)"
    }

    fun mostrarDetalle() {
        println("--------- DETALLE DEL CARRITO ---------")
        if (productos.isEmpty()) {
            println("(El carrito está vacío)")
        } else {
            productos.forEachIndexed { index, p ->
                println("${index + 1}. $p")
            }
        }
        println("----------------------------------------")
    }

    fun mostrarResumen() {
        mostrarDetalle()
        println("Cantidad de productos : $cantidadProductos")

        val subtotal = calcularSubtotal()
        val igv = calcularIGV(subtotal)
        val total = calcularTotal(subtotal, igv)

        println(String.format("%-22s: S/ %8.2f", "Subtotal", subtotal))
        println(String.format("%-22s: S/ %8.2f", "IGV (18%)", igv))
        println(String.format("%-22s: S/ %8.2f", "TOTAL A PAGAR", total))
        println("----------------------------------------")

        productoMasCaro()?.let {
            println("Producto mas caro: ${it.nombre} " + String.format("(S/ %.2f)", it.precio))
        }

        val descuento = calcularDescuento(total)
        val totalConDescuento = total - descuento
        println(descripcionDescuento(total))
        println(String.format("%-22s: S/ %8.2f", "TOTAL CON DESCUENTO", totalConDescuento))
    }

    fun despedida() {
        println()
        println("Gracias por su compra, ${cliente.nombre}!")
    }
}

/**
 * Maneja la interacción por consola (el "prompt"): muestra el menú,
 * lee la entrada del usuario y delega cada acción al Carrito.
 */
class MenuConsola(private val carrito: Carrito) {

    fun iniciar() {
        var salir = false
        while (!salir) {
            mostrarMenu()
            when (readLine()?.trim()) {
                "1" -> agregarProductoInteractivo()
                "2" -> eliminarProductoInteractivo()
                "3" -> buscarProductoInteractivo()
                "4" -> carrito.mostrarResumen()
                "5" -> {
                    carrito.despedida()
                    salir = true
                }
                else -> println("Opción inválida. Intente nuevamente.")
            }
            println()
        }
    }

    private fun mostrarMenu() {
        println("========== MENÚ CARRITO DE COMPRAS ==========")
        println("1. Agregar producto")
        println("2. Eliminar producto")
        println("3. Buscar producto")
        println("4. Ver detalle y total")
        println("5. Salir")
        print("Seleccione una opción: ")
    }

    private fun agregarProductoInteractivo() {
        print("Nombre del producto: ")
        val nombre = readLine()?.trim().orEmpty()

        print("Precio (S/): ")
        val precio = readLine()?.trim()?.toDoubleOrNull()

        print("Cantidad: ")
        val cantidad = readLine()?.trim()?.toIntOrNull()

        if (nombre.isBlank() || precio == null || cantidad == null || precio <= 0 || cantidad <= 0) {
            println("Datos inválidos. No se agregó el producto.")
            return
        }
        carrito.agregarProducto(Producto(nombre, precio, cantidad))
    }

    private fun eliminarProductoInteractivo() {
        print("Nombre del producto a eliminar: ")
        val nombre = readLine()?.trim().orEmpty()
        carrito.eliminarProducto(nombre)
    }

    private fun buscarProductoInteractivo() {
        print("Nombre del producto a buscar: ")
        val nombre = readLine()?.trim().orEmpty()
        val encontrado = carrito.buscarProducto(nombre)
        println("Resultado: ${encontrado ?: "no encontrado"}")
    }
}

fun main() {
    println("=========================================")
    println("   CARRITO DE COMPRAS - TIENDA TECSUP")
    println("=========================================")

    print("Ingrese el nombre del cliente: ")
    val nombreCliente = readLine()?.trim().let { if (it.isNullOrBlank()) "Cliente" else it }

    val cliente = Cliente(nombreCliente)
    val carrito = Carrito(cliente)
    println("Cliente: ${cliente.nombre}")
    println()

    MenuConsola(carrito).iniciar()
    exitProcess(0)
}