package com.correa.actividad_semana_6

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.correa.actividad_semana_6.data.Cita
import com.correa.actividad_semana_6.data.citasIniciales
import com.correa.actividad_semana_6.navigation.Screen
import com.correa.actividad_semana_6.screens.AgendarScreen
import com.correa.actividad_semana_6.screens.InicioScreen
import com.correa.actividad_semana_6.screens.PerfilMedicoScreen
import kotlinx.coroutines.launch

@Composable
fun ClinicaApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val citas = remember {
        mutableStateListOf<Cita>().apply {
            addAll(citasIniciales)
        }
    }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute,
                onDestinationClick = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(Screen.Inicio.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Inicio.route
        ) {
            composable(Screen.Inicio.route) {
                InicioScreen(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onMedicoClick = { medicoId ->
                        navController.navigate(Screen.PerfilMedico.createRoute(medicoId))
                    }
                )
            }

            composable(Screen.MisCitas.route) {
                PantallaEnConstruccion(titulo = "Mis citas")
            }

            composable(Screen.Historial.route) {
                PantallaEnConstruccion(titulo = "Historial médico")
            }

            composable(Screen.Perfil.route) {
                PantallaEnConstruccion(titulo = "Perfil")
            }

            composable(
                route = Screen.PerfilMedico.route,
                arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
            ) { entry ->
                val medicoId = entry.arguments?.getInt("medicoId") ?: 0
                PerfilMedicoScreen(
                    medicoId = medicoId,
                    onBack = { navController.popBackStack() },
                    onAgendarCita = {
                        navController.navigate(Screen.AgendarCita.createRoute(medicoId))
                    }
                )
            }

            composable(
                route = Screen.AgendarCita.route,
                arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
            ) { entry ->
                val medicoId = entry.arguments?.getInt("medicoId") ?: 0
                AgendarScreen(
                    medicoId = medicoId,
                    onBack = { navController.popBackStack() },
                    onConfirmar = { id, fechaIdx, horaIdx ->
                        navController.navigate(Screen.Confirmacion.createRoute(id, fechaIdx, horaIdx))
                    }
                )
            }

            composable(
                route = Screen.Confirmacion.route,
                arguments = listOf(
                    navArgument("medicoId") { type = NavType.IntType },
                    navArgument("fechaIdx") { type = NavType.IntType },
                    navArgument("horaIdx") { type = NavType.IntType }
                )
            ) {
                PantallaEnConstruccion(titulo = "Confirmación")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PantallaEnConstruccion(titulo: String) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = titulo,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
}

@Composable
private fun DrawerContent(
    currentRoute: String?,
    onDestinationClick: (String) -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.fillMaxHeight()
    ) {
        DrawerHeader()
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(8.dp))

        DrawerItem(
            icon = Icons.Filled.Home,
            label = "Inicio",
            route = Screen.Inicio.route,
            currentRoute = currentRoute,
            onClick = onDestinationClick
        )
        DrawerItem(
            icon = Icons.Filled.CalendarMonth,
            label = "Mis citas",
            route = Screen.MisCitas.route,
            currentRoute = currentRoute,
            onClick = onDestinationClick
        )
        DrawerItem(
            icon = Icons.Filled.MedicalServices,
            label = "Historial médico",
            route = Screen.Historial.route,
            currentRoute = currentRoute,
            onClick = onDestinationClick
        )
        DrawerItem(
            icon = Icons.Filled.Person,
            label = "Perfil",
            route = Screen.Perfil.route,
            currentRoute = currentRoute,
            onClick = onDestinationClick
        )
    }
}

@Composable
private fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(
                modifier = Modifier.size(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JP",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Juan Pérez",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Paciente",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerItem(
    icon: ImageVector,
    label: String,
    route: String,
    currentRoute: String?,
    onClick: (String) -> Unit
) {
    val selected = currentRoute == route
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationDrawerItem(
            icon = {
                Surface(
                    shape = CircleShape,
                    color = if (selected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (selected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            },
            label = {
                Text(
                    text = label,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                )
            },
            selected = selected,
            onClick = { onClick(route) },
            badge = { Spacer(modifier = Modifier.width(0.dp)) },
            modifier = Modifier.clip(MaterialTheme.shapes.large),
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedContainerColor = com.correa.actividad_semana_6.ui.theme.Blanco,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}