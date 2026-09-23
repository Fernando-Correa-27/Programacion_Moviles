package com.correa.tecsupfit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.correa.tecsupfit.data.Reserva
import com.correa.tecsupfit.data.reservasIniciales
import com.correa.tecsupfit.navigation.Screen
import com.correa.tecsupfit.screens.InicioFitScreen

private val tabRutas = setOf(
    Screen.Inicio.route,
    Screen.Reservas.route,
    Screen.Rutinas.route,
    Screen.Perfil.route
)

@Composable
fun FitApp() {
    val navController = rememberNavController()
    val reservas = remember {
        mutableStateListOf<Reserva>().apply {
            addAll(reservasIniciales)
        }
    }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val mostrarBottomBar = currentRoute != null && currentRoute in tabRutas

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            when (currentRoute) {
                Screen.Inicio.route -> TopBarFit(mostrarMenu = false)
                else -> {}
            }
        },
        bottomBar = {
            if (mostrarBottomBar) {
                FitBottomBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Inicio.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Inicio.route) {
                InicioFitScreen(
                    onClaseClick = { claseId ->
                        navController.navigate(Screen.DetalleClase.createRoute(claseId))
                    }
                )
            }

            composable(Screen.Reservas.route) {
                PantallaEnConstruccion(titulo = "Mis reservas")
            }

            composable(Screen.Rutinas.route) {
                PantallaEnConstruccion(titulo = "Rutinas")
            }

            composable(Screen.Perfil.route) {
                PantallaEnConstruccion(titulo = "Mi perfil")
            }

            composable(
                route = Screen.DetalleClase.route,
                arguments = listOf(navArgument("claseId") { type = NavType.IntType })
            ) {
                PantallaEnConstruccion(titulo = "Detalle de clase")
            }

            composable(
                route = Screen.Confirmacion.route,
                arguments = listOf(
                    navArgument("claseId") { type = NavType.IntType },
                    navArgument("horarioIdx") { type = NavType.IntType }
                )
            ) {
                PantallaEnConstruccion(titulo = "Confirmación")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarFit(
    mostrarMenu: Boolean,
    saludo: String = "Hola, Diego"
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "TECSUP Fit",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = saludo,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun FitBottomBar(
    navController: NavController,
    currentRoute: String?
) {
    val items = listOf(
        BarraItem(Icons.Filled.Home, "Inicio", Screen.Inicio.route),
        BarraItem(Icons.Filled.CalendarMonth, "Reservas", Screen.Reservas.route),
        BarraItem(Icons.Filled.FitnessCenter, "Rutinas", Screen.Rutinas.route),
        BarraItem(Icons.Filled.Person, "Perfil", Screen.Perfil.route)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.navigationBarsPadding()
    ) {
        items.forEach { item ->
            val seleccionado = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium
                    )
                },
                selected = seleccionado,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Screen.Inicio.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

private data class BarraItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

@Composable
fun PantallaEnConstruccion(titulo: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$titulo - en construcción",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}