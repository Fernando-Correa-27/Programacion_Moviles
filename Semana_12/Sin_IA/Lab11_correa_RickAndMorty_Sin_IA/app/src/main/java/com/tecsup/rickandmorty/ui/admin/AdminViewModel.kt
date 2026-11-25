package com.tecsup.rickandmorty.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.rickandmorty.data.repository.ItemRepository
import com.tecsup.rickandmorty.notification.Notificador
import com.tecsup.rickandmorty.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Totales(val guardados: Int, val favoritos: Int)

class AdminViewModel(private val repository: ItemRepository, private val notificador: Notificador) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Totales>>(UiState.Cargando)
    val uiState = _uiState.asStateFlow()

    init { actualizarTotales() }
    fun actualizarTotales() {
        viewModelScope.launch { _uiState.value = UiState.Exito(Totales(repository.cantidadGuardada(), repository.cantidadFavoritos())) }
    }
    fun sincronizar() {
        viewModelScope.launch {
            repository.refrescar().onSuccess { cantidad ->
                notificador.mostrar("Datos actualizados", "Se guardaron $cantidad elementos")
            }
            actualizarTotales()
        }
    }
    fun limpiarCache() {
        viewModelScope.launch {
            repository.limpiarCache()
            actualizarTotales()
        }
    }
}
