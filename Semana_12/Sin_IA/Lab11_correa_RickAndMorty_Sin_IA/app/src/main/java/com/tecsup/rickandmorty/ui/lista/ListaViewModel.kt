package com.tecsup.rickandmorty.ui.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.rickandmorty.data.repository.ItemRepository
import com.tecsup.rickandmorty.domain.model.Item
import com.tecsup.rickandmorty.notification.Notificador
import com.tecsup.rickandmorty.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ListaViewModel(private val repository: ItemRepository, private val notificador: Notificador) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Item>>>(UiState.Cargando)
    val uiState = _uiState.asStateFlow()
    private val _avisoSinConexion = MutableStateFlow<String?>(null)
    val avisoSinConexion = _avisoSinConexion.asStateFlow()
    private var observandoRoom = false

    init { refrescar() }

    fun refrescar() {
        viewModelScope.launch {
            if (_uiState.value is UiState.Error) _uiState.value = UiState.Cargando
            _avisoSinConexion.value = null
            repository.refrescar().fold(
                onSuccess = { cantidad ->
                    val lista = repository.items.first()
                    _uiState.value = UiState.Exito(lista)
                    observarRoom()
                    notificador.mostrar("Datos actualizados", "Se guardaron $cantidad elementos")
                },
                onFailure = { error ->
                    val cantidad = repository.cantidadGuardada()
                    if (cantidad == 0) _uiState.value = UiState.Error(mensajeError(error))
                    else {
                        _uiState.value = UiState.Exito(repository.items.first())
                        _avisoSinConexion.value = "Sin conexión: mostrando datos guardados"
                        observarRoom()
                    }
                }
            )
        }
    }

    fun alternarFavorito(id: Int) { viewModelScope.launch { repository.alternarFavorito(id) } }

    private fun observarRoom() {
        if (observandoRoom) return
        observandoRoom = true
        viewModelScope.launch { repository.items.collect { _uiState.value = UiState.Exito(it) } }
    }

    private fun mensajeError(error: Throwable): String = when (error) {
        is IOException -> "No hay conexión a internet"
        is HttpException -> "El servidor respondió con error ${error.code()}"
        else -> "Ocurrió un error inesperado"
    }
}
