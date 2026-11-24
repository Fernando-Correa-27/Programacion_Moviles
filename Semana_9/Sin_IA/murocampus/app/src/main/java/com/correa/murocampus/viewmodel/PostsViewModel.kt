package com.correa.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correa.murocampus.data.PostRepository
import com.correa.murocampus.data.mensajeDe
import com.correa.murocampus.model.Comentario
import com.correa.murocampus.model.Post
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetalleUi(
    val post: Post,
    val comentarios: List<Comentario>
)

class PostsViewModel(
    private val repo: PostRepository
) : ViewModel() {

    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista: StateFlow<UiState<List<Post>>> = _lista.asStateFlow()

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle: StateFlow<UiState<DetalleUi>> = _detalle.asStateFlow()

    private val _idSeleccionado = MutableStateFlow<Int?>(null)
    val idSeleccionado: StateFlow<Int?> = _idSeleccionado.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _lista.value = UiState.Cargando
            repo.listar()
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun abrir(id: Int) {
        _idSeleccionado.value = id
        _detalle.value = UiState.Cargando
        viewModelScope.launch {
            coroutineScope {
                val postDeferred = async { repo.detalle(id) }
                val comentariosDeferred = async { repo.comentarios(id) }

                val post = postDeferred.await()
                val comentarios = comentariosDeferred.await()

                if (post.isSuccess && comentarios.isSuccess) {
                    _detalle.value = UiState.Exito(
                        DetalleUi(post.getOrThrow(), comentarios.getOrThrow())
                    )
                } else {
                    val error = post.exceptionOrNull()
                        ?: comentarios.exceptionOrNull()
                        ?: Exception("Error desconocido")
                    _detalle.value = UiState.Error(mensajeDe(error))
                }
            }
        }
    }

    fun volverALista() {
        _idSeleccionado.value = null
    }
}
