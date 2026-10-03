package com.tecsup.mibodega.ui.cliente

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaCategorias
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.componentes.SeccionCliente
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.categorias.CategoriasScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.PedidosScreen
import com.tecsup.mibodega.ui.cliente.screens.perfil.PerfilScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen


private object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val REGISTRO = "registro"
    const val INICIO = "inicio"
    const val DETALLE = "detalle/{productoId}"
    const val CARRITO = "carrito"
    const val ENTREGA = "entrega"
    const val CONFIRMACION = "confirmacion"
    const val CATEGORIAS = "categorias"
    const val PEDIDOS = "pedidos"
    const val PERFIL = "perfil"

    fun detalle(productoId: Int) = "detalle/$productoId"
}

@Composable
fun ClienteApp() {
    val navController = rememberNavController()

    // El carrito vive aquí arriba, no en ninguna Screen.
    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var categoriaActual by remember { mutableStateOf(listaCategorias.first()) }
    var pedidoConfirmado by remember { mutableStateOf(false) }
    var totalUltimoPedido by remember { mutableStateOf(0.0) }
    var perfilCliente by remember {
        mutableStateOf(listOf("Juan Pérez", "987 654 321", "Av. Los Olivos 123", "Frente al parque"))
    }
    var datosPedido by remember {
        mutableStateOf(listOf("Juan Pérez", "987 654 321", "Av. Los Olivos 123", "Frente al parque", "Efectivo al entregar"))
    }
    val onSeccionSeleccionada: (SeccionCliente) -> Unit = { seccion ->
        val ruta = when (seccion) {
            SeccionCliente.INICIO -> Rutas.INICIO
            SeccionCliente.CATEGORIAS -> Rutas.CATEGORIAS
            SeccionCliente.PEDIDOS -> Rutas.PEDIDOS
            SeccionCliente.PERFIL -> Rutas.PERFIL
        }
        if (seccion == SeccionCliente.INICIO) {
            navController.popBackStack(Rutas.INICIO, false)
        } else {
            navController.navigate(ruta) {
                popUpTo(Rutas.INICIO) { inclusive = false }
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Rutas.BIENVENIDA
    ) {
        composable(Rutas.BIENVENIDA) {
            BienvenidaScreen(
                onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                onIniciarSesion = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                },
                onTerminos = { /* TODO: abrir términos y condiciones */ }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroScreen(
                onVolver = { navController.popBackStack() },
                onCrearCuenta = { nombre, telefono, direccion, referencia ->
                    perfilCliente = listOf(nombre, telefono, direccion, referencia)
                    datosPedido = listOf(nombre, telefono, direccion, referencia, "Efectivo al entregar")
                    // TODO: guardar estos datos cuando exista el registro real
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.INICIO) {
            InicioScreen(
                cantidadCarrito = carrito.sumOf { it.cantidad },
                onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                },
                categoriaSeleccionada = categoriaActual,
                onCategoriaSeleccionada = { categoriaActual = it },
                onSeccionSeleccionada = onSeccionSeleccionada
            )
        }

        composable(Rutas.CATEGORIAS) {
            CategoriasScreen(
                onCategoriaSeleccionada = { categoria ->
                    categoriaActual = categoria
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.INICIO) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSeccionSeleccionada = onSeccionSeleccionada
            )
        }

        composable(Rutas.PEDIDOS) {
            PedidosScreen(
                pedidoConfirmado = pedidoConfirmado,
                total = totalUltimoPedido,
                direccion = datosPedido[2],
                onIrAlCatalogo = {
                    navController.popBackStack(Rutas.INICIO, false)
                },
                onSeccionSeleccionada = onSeccionSeleccionada
            )
        }

        composable(Rutas.PERFIL) {
            PerfilScreen(
                nombre = perfilCliente[0],
                telefono = perfilCliente[1],
                direccion = perfilCliente[2],
                referencia = perfilCliente[3],
                onSeccionSeleccionada = onSeccionSeleccionada
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            val producto = listaProductosFake.first { it.id == productoId }

            DetalleProductoScreen(
                producto = producto,
                onVolver = { navController.popBackStack() },
                onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                    carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                    navController.popBackStack()
                }
            )
        }

        composable(Rutas.CARRITO) {
            CarritoScreen(
                carrito = carrito,
                onVolver = { navController.popBackStack() },
                onIncrementar = { producto ->
                    carrito = carrito.map {
                        if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + 1) else it
                    }
                },
                onDecrementar = { producto ->
                    carrito = carrito.mapNotNull {
                        when {
                            it.producto.id != producto.id -> it
                            it.cantidad > 1 -> it.copy(cantidad = it.cantidad - 1)
                            else -> null // si llega a 0, se elimina de la lista
                        }
                    }
                },
                onEliminar = { producto ->
                    carrito = carrito.filterNot { it.producto.id == producto.id }
                },
                onContinuarPedido = {
                    if (carrito.isNotEmpty()) navController.navigate(Rutas.ENTREGA)
                }
            )
        }

        composable(Rutas.ENTREGA) {
            DatosEntregaScreen(
                onVolver = { navController.popBackStack() },
                onConfirmarPedido = { nombre, telefono, direccion, referencia, metodoPago ->
                    datosPedido = listOf(nombre, telefono, direccion, referencia, metodoPago)
                    perfilCliente = listOf(nombre, telefono, direccion, referencia)
                    totalUltimoPedido = carrito.sumOf { it.producto.precio * it.cantidad } + 4.0
                    pedidoConfirmado = true
                    navController.navigate(Rutas.CONFIRMACION) {
                        popUpTo(Rutas.CARRITO) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.CONFIRMACION) {
            ConfirmacionScreen(
                total = totalUltimoPedido,
                nombre = datosPedido[0],
                direccion = datosPedido[2],
                referencia = datosPedido[3],
                onVolverInicio = {
                    carrito = emptyList()
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.INICIO) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {
    val itemExistente = carrito.find { it.producto.id == producto.id }
    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
        }
    } else {
        carrito + ItemCarrito(producto = producto, cantidad = cantidad)
    }
}
