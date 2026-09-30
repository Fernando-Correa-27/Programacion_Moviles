package com.correa.tecsup_store.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.correa.tecsup_store.navigation.Screen

/**
 * Estructura base del NavigationDrawer: ModalNavigationDrawer envuelve al
 * Scaffold existente y la hoja contiene las secciones de la tienda.
 */
@Composable
fun TiendaNavigationDrawer(
    drawerState: DrawerState,
    onNavegar: (Screen) -> Unit,
    contenido: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Screen.seccionesDelDrawer.forEach { seccion ->
                        NavigationDrawerItem(
                            label = { Text(seccion.titulo) },
                            icon = {},
                            selected = false,
                            onClick = { onNavegar(seccion) },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    ) {
        contenido()
    }
}
