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
import com.tecsup.mibodega.ui.cliente.modelo.cantidadTotalProductos
import com.tecsup.mibodega.ui.cliente.modelo.ModalidadEntrega
import com.tecsup.mibodega.ui.cliente.modelo.calcularTotalPedido
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.Pedido
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
import com.tecsup.mibodega.ui.cliente.screens.favoritos.FavoritosScreen


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
    const val FAVORITOS = "favoritos"

    fun detalle(productoId: Int) = "detalle/$productoId"
}

@Composable
fun ClienteApp(
    modoOscuro: Boolean = false,
    onModoOscuroCambia: (Boolean) -> Unit = {}
) {
    val navController = rememberNavController()

    // El carrito vive aquí arriba, no en ninguna Screen.
    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var modalidadEntrega by remember { mutableStateOf(ModalidadEntrega.DELIVERY) }
    var categoriaActual by remember { mutableStateOf(listaCategorias.first()) }
    var pedidos by remember { mutableStateOf<List<Pedido>>(emptyList()) }
    var favoritosIds by remember { mutableStateOf<Set<Int>>(emptySet()) }
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
                cantidadCarrito = carrito.cantidadTotalProductos(),
                onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                },
                categoriaSeleccionada = categoriaActual,
                onCategoriaSeleccionada = { categoriaActual = it },
                onSeccionSeleccionada = onSeccionSeleccionada,
                favoritos = favoritosIds,
                onCambiarFavorito = { favoritosIds = alternarFavorito(favoritosIds, it.id) },
                onVerFavoritos = { navController.navigate(Rutas.FAVORITOS) }
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
                pedidos = pedidos,
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
                onSeccionSeleccionada = onSeccionSeleccionada,
                modoOscuro = modoOscuro,
                onModoOscuroCambia = onModoOscuroCambia
            )
        }

        composable(Rutas.FAVORITOS) {
            FavoritosScreen(
                productos = listaProductosFake.filter { it.id in favoritosIds },
                onVolver = { navController.popBackStack() },
                onProductoClick = { navController.navigate(Rutas.detalle(it.id)) },
                onAgregarProducto = { carrito = agregarOSumarProducto(carrito, it, 1) },
                onQuitarFavorito = { favoritosIds = favoritosIds - it.id }
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
                esFavorito = producto.id in favoritosIds,
                onCambiarFavorito = { favoritosIds = alternarFavorito(favoritosIds, producto.id) },
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
                },
                modalidadEntrega = modalidadEntrega,
                onModalidadEntregaCambia = { modalidadEntrega = it }
            )
        }

        composable(Rutas.ENTREGA) {
            DatosEntregaScreen(
                onVolver = { navController.popBackStack() },
                modalidadEntrega = modalidadEntrega,
                onConfirmarPedido = { nombre, telefono, direccion, referencia, metodoPago ->
                    val datosCliente = if (modalidadEntrega == ModalidadEntrega.DELIVERY) {
                        listOf(nombre, telefono, direccion, referencia)
                    } else {
                        perfilCliente
                    }
                    perfilCliente = datosCliente
                    datosPedido = datosCliente + metodoPago
                    val direccionPedido = if (modalidadEntrega == ModalidadEntrega.DELIVERY) {
                        direccion
                    } else {
                        "Recojo en tienda"
                    }
                    val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
                    pedidos = pedidos + Pedido(
                        id = (pedidos.lastOrNull()?.id ?: 1023) + 1,
                        productos = carrito.toList(),
                        total = calcularTotalPedido(subtotal, modalidadEntrega),
                        direccion = direccionPedido,
                        modalidad = modalidadEntrega
                    )
                    navController.navigate(Rutas.CONFIRMACION) {
                        popUpTo(Rutas.CARRITO) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.CONFIRMACION) {
            ConfirmacionScreen(
                total = pedidos.lastOrNull()?.total ?: 0.0,
                nombre = datosPedido[0],
                direccion = datosPedido[2],
                referencia = datosPedido[3],
                modalidadEntrega = pedidos.lastOrNull()?.modalidad ?: ModalidadEntrega.DELIVERY,
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

private fun alternarFavorito(actuales: Set<Int>, productoId: Int): Set<Int> =
    if (productoId in actuales) actuales - productoId else actuales + productoId

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
