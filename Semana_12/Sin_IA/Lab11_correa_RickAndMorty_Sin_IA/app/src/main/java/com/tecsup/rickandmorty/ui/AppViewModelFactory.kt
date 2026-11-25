package com.tecsup.rickandmorty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.rickandmorty.AppContainer
import com.tecsup.rickandmorty.ui.admin.AdminViewModel
import com.tecsup.rickandmorty.ui.detalle.DetalleViewModel
import com.tecsup.rickandmorty.ui.favoritos.FavoritosViewModel
import com.tecsup.rickandmorty.ui.lista.ListaViewModel
import com.tecsup.rickandmorty.ui.login.LoginViewModel

class AppViewModelFactory(
    private val container: AppContainer,
    private val itemId: Int? = null
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(container.authRepository)
        modelClass.isAssignableFrom(ListaViewModel::class.java) -> ListaViewModel(container.itemRepository, container.notificador)
        modelClass.isAssignableFrom(FavoritosViewModel::class.java) -> FavoritosViewModel(container.itemRepository)
        modelClass.isAssignableFrom(AdminViewModel::class.java) -> AdminViewModel(container.itemRepository, container.notificador)
        modelClass.isAssignableFrom(DetalleViewModel::class.java) -> DetalleViewModel(container.itemRepository, requireNotNull(itemId))
        else -> error("ViewModel no registrado: ${modelClass.name}")
    } as T
}
