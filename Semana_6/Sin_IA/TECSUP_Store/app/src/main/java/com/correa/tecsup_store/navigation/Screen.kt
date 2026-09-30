package com.correa.tecsup_store.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Contrato central de navegación, adaptado del Laboratorio 5 (NavLab).
 * Cada subsección de la tienda es una ruta declarada aquí.
 */
sealed class Screen(val route: String, val titulo: String, val icono: ImageVector) {

    object Home : Screen(route = "home", titulo = "Inicio", icono = Icons.Default.Home)

    object Cart : Screen(route = "carrito", titulo = "Mi carrito", icono = Icons.Default.ShoppingCart)

    object Favorites : Screen(route = "favoritos", titulo = "Favoritos", icono = Icons.Default.Favorite)

    object Profile : Screen(route = "perfil", titulo = "Mi perfil", icono = Icons.Default.Person)

    object Detail : Screen(route = "detalle/{productoId}", titulo = "Detalle", icono = Icons.Default.Home) {

        fun createRoute(productoId: Int): String = "detalle/$productoId"
    }

    companion object {
        /** Secciones que se muestran dentro del NavigationDrawer. */
        val seccionesDelDrawer = listOf(Home, Cart, Favorites, Profile)
    }
}
