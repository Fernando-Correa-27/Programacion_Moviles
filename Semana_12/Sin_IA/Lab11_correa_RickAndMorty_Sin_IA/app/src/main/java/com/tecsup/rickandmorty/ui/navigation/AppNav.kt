package com.tecsup.rickandmorty.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.rickandmorty.AppContainer
import com.tecsup.rickandmorty.domain.model.Rol
import com.tecsup.rickandmorty.domain.model.Sesion
import com.tecsup.rickandmorty.ui.AppViewModelFactory
import com.tecsup.rickandmorty.ui.UiState
import com.tecsup.rickandmorty.ui.admin.AdminScreen
import com.tecsup.rickandmorty.ui.admin.AdminViewModel
import com.tecsup.rickandmorty.ui.components.Cargando
import com.tecsup.rickandmorty.ui.detalle.DetalleScreen
import com.tecsup.rickandmorty.ui.detalle.DetalleViewModel
import com.tecsup.rickandmorty.ui.favoritos.FavoritosScreen
import com.tecsup.rickandmorty.ui.favoritos.FavoritosViewModel
import com.tecsup.rickandmorty.ui.lista.ListaScreen
import com.tecsup.rickandmorty.ui.lista.ListaViewModel
import com.tecsup.rickandmorty.ui.login.LoginScreen
import com.tecsup.rickandmorty.ui.login.LoginViewModel

@Composable
fun AppNav(container: AppContainer) {
    val loginViewModel: LoginViewModel = viewModel(factory = AppViewModelFactory(container))
    val sesionState by loginViewModel.uiState.collectAsStateWithLifecycle()
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }

    when (val estado = sesionState) {
        UiState.Cargando -> Cargando(Modifier.fillMaxSize())
        is UiState.Error -> LoginScreen(
            estado, correo, clave, { correo = it }, { clave = it },
            { loginViewModel.iniciarSesion(correo, clave) }, loginViewModel::revisarSesion
        )
        is UiState.Exito -> {
            val sesion = estado.datos
            if (sesion == null) LoginScreen(
                estado, correo, clave, { correo = it }, { clave = it },
                { loginViewModel.iniciarSesion(correo, clave) }, loginViewModel::revisarSesion
            ) else CatalogoNav(container, sesion, loginViewModel)
        }
    }
}

@Composable
private fun CatalogoNav(container: AppContainer, sesion: Sesion, loginViewModel: LoginViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") {
            val vm: ListaViewModel = viewModel(factory = AppViewModelFactory(container))
            val estado by vm.uiState.collectAsStateWithLifecycle()
            val aviso by vm.avisoSinConexion.collectAsStateWithLifecycle()
            ListaScreen(
                estado, aviso, sesion.rol == Rol.ADMIN, vm::refrescar,
                { id -> navController.navigate("detalle/$id") }, vm::alternarFavorito,
                { navController.navigate("favoritos") }, { navController.navigate("admin") },
                loginViewModel::cerrarSesion
            )
        }
        composable("favoritos") {
            val vm: FavoritosViewModel = viewModel(factory = AppViewModelFactory(container))
            val estado by vm.uiState.collectAsStateWithLifecycle()
            FavoritosScreen(estado, { id -> navController.navigate("detalle/$id") }, vm::alternarFavorito)
        }
        composable("detalle/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) { entry ->
            val id = requireNotNull(entry.arguments?.getInt("id"))
            val vm: DetalleViewModel = viewModel(key = "detalle-$id", factory = AppViewModelFactory(container, id))
            val estado by vm.uiState.collectAsStateWithLifecycle()
            DetalleScreen(estado, vm::cargarDetalle, vm::alternarFavorito)
        }
        composable("admin") {
            if (sesion.rol != Rol.ADMIN) AccesoDenegado { navController.popBackStack() }
            else {
                val vm: AdminViewModel = viewModel(factory = AppViewModelFactory(container))
                val estado by vm.uiState.collectAsStateWithLifecycle()
                AdminScreen(estado, vm::sincronizar, vm::limpiarCache)
            }
        }
    }
}

@Composable
private fun AccesoDenegado(alVolver: () -> Unit) {
    Scaffold { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No tienes permiso para abrir esta sección.")
            Button(onClick = alVolver) { Text("Volver al catálogo") }
        }
    }
}
