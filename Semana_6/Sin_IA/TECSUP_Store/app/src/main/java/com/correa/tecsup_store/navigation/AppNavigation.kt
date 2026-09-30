package com.correa.tecsup_store.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Storefront
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.correa.tecsup_store.data.TiendaEstado
import com.correa.tecsup_store.model.CatalogoTienda
import com.correa.tecsup_store.ui.screens.CartScreen
import com.correa.tecsup_store.ui.screens.FavoritesScreen
import com.correa.tecsup_store.ui.screens.HomeScreen
import com.correa.tecsup_store.ui.screens.ProductDetailScreen
import com.correa.tecsup_store.ui.screens.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(estado: TiendaEstado) {

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

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
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = "TECSUP Store",
                            tint = Color.White,
                            modifier = Modifier.padding(start = 16.dp)
                        )
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
