package com.correa.tecsup_store.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.correa.tecsup_store.data.TiendaEstado
import com.correa.tecsup_store.model.CatalogoTienda
import com.correa.tecsup_store.ui.components.TiendaNavigationDrawer
import com.correa.tecsup_store.ui.screens.CartScreen
import com.correa.tecsup_store.ui.screens.FavoritesScreen
import com.correa.tecsup_store.ui.screens.HomeScreen
import com.correa.tecsup_store.ui.screens.ProductDetailScreen
import com.correa.tecsup_store.ui.screens.ProfileScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(estado: TiendaEstado) {

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    val tituloActual = remember(rutaActual) {
        when {
            rutaActual == Screen.Cart.route -> Screen.Cart.titulo
            rutaActual == Screen.Favorites.route -> Screen.Favorites.titulo
            rutaActual == Screen.Profile.route -> Screen.Profile.titulo
            rutaActual == Screen.Detail.route -> Screen.Detail.titulo
            else -> Screen.Home.titulo
        }
    }

    LaunchedEffect(estado.mensaje) {
        estado.mensaje?.let {
            snackbarHostState.showSnackbar(it)
            estado.limpiarMensaje()
        }
    }

    // ModalNavigationDrawer envuelve al Scaffold existente sin romperlo
    TiendaNavigationDrawer(
        drawerState = drawerState,
        rutaActual = rutaActual,
        unidadesEnCarrito = estado.unidadesEnCarrito,
        totalFavoritos = estado.favoritos.size,
        onNavegar = { seccion ->
            navController.navigate(seccion.route) {
                // Evita apilar la misma seccion varias veces
                popUpTo(Screen.Home.route) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
            scope.launch { drawerState.close() }
        },
        onCerrarDrawer = { scope.launch { drawerState.close() } }
    ) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = tituloActual,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    if (rutaActual == Screen.Detail.route) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                    } else {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú de navegación",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Home.route) {
                HomeScreen(
                    estado = estado,
                    onVerDetalle = { producto ->
                        navController.navigate(Screen.Detail.createRoute(producto.id))
                    }
                )
            }

            composable(Screen.Cart.route) {
                CartScreen(estado = estado)
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    estado = estado,
                    onVerDetalle = { producto ->
                        navController.navigate(Screen.Detail.createRoute(producto.id))
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(estado = estado)
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("productoId") { type = NavType.IntType })
            ) { entry ->
                val productoId = entry.arguments?.getInt("productoId") ?: 0
                val producto = CatalogoTienda.productos.firstOrNull { it.id == productoId }
                    ?: CatalogoTienda.productos.first()

                ProductDetailScreen(
                    producto = producto,
                    estado = estado
                )
            }
        }
    }
    }
}
