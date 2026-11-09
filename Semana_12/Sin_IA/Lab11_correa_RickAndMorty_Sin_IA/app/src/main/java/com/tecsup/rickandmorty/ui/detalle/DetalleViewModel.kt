package com.tecsup.rickandmorty.ui.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.rickandmorty.data.repository.ItemRepository
import com.tecsup.rickandmorty.domain.model.Item
import com.tecsup.rickandmorty.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetalleViewModel(private val repository: ItemRepository, private val id: Int) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Item>>(UiState.Cargando)
    val uiState = _uiState.asStateFlow()

    init { cargarDetalle() }
    fun cargarDetalle() {
        viewModelScope.launch {
            _uiState.value = UiState.Cargando
            repository.obtenerDetalle(id).fold(
                { _uiState.value = UiState.Exito(it) },
                { _uiState.value = UiState.Error(mensajeError(it)) }
            )
        }
    }
    fun alternarFavorito() {
        viewModelScope.launch {
            repository.alternarFavorito(id)
            repository.items.first().firstOrNull { it.id == id }?.let { _uiState.value = UiState.Exito(it) }
        }
    }

    private fun mensajeError(error: Throwable) = when (error) {
        is IOException -> "No hay conexión a internet"
        is HttpException -> "El servidor respondió con error ${error.code()}"
        else -> "Ocurrió un error inesperado"
    }
}
