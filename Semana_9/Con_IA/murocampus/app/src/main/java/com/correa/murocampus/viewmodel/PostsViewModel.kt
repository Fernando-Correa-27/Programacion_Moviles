package com.correa.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correa.murocampus.data.PostRepository
import com.correa.murocampus.data.mensajeDe
import com.correa.murocampus.model.Comentario
import com.correa.murocampus.model.NuevoPost
import com.correa.murocampus.model.Post
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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

    private val _consulta = MutableStateFlow("")
    val consulta: StateFlow<String> = _consulta.asStateFlow()

    // La búsqueda se aplica sobre lo ya descargado y respeta el filtro por usuario.
    val listaVisible: StateFlow<UiState<List<Post>>> =
        combine(_lista, _consulta) { estado, texto ->
            filtrarPorTitulo(estado, texto)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = _lista.value
        )

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle: StateFlow<UiState<DetalleUi>> = _detalle.asStateFlow()

    private val _idSeleccionado = MutableStateFlow<Int?>(null)
    val idSeleccionado: StateFlow<Int?> = _idSeleccionado.asStateFlow()

    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro: StateFlow<Int?> = _usuarioFiltro.asStateFlow()

    private val _creacion = MutableStateFlow<UiState<Post>?>(null)
    val creacion: StateFlow<UiState<Post>?> = _creacion.asStateFlow()

    private val _eliminacion = MutableStateFlow<UiState<Unit>?>(null)
    val eliminacion: StateFlow<UiState<Unit>?> = _eliminacion.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _lista.value = UiState.Cargando
            val filtro = _usuarioFiltro.value
            val resultado = if (filtro == null) {
                repo.listar()
            } else {
                repo.porUsuario(filtro)
            }
            resultado
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    fun buscar(texto: String) {
        _consulta.value = texto
    }

    fun abrir(id: Int) {
        _eliminacion.value = null
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
        _eliminacion.value = null
        _idSeleccionado.value = null
    }

    fun publicar(titulo: String, texto: String) {
        if (titulo.isBlank() || texto.isBlank()) return

        _creacion.value = UiState.Cargando
        viewModelScope.launch {
            repo.crear(NuevoPost(userId = 1, title = titulo, body = texto))
                .onSuccess { _creacion.value = UiState.Exito(it) }
                .onFailure { _creacion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun eliminar(id: Int) {
        if (_eliminacion.value == UiState.Cargando) return

        _eliminacion.value = UiState.Cargando
        viewModelScope.launch {
            repo.borrar(id)
                .onSuccess {
                    quitarDeLista(id)
                    _eliminacion.value = null
                    volverALista()
                }
                .onFailure { _eliminacion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    private fun quitarDeLista(id: Int) {
        val actual = _lista.value
        if (actual is UiState.Exito) {
            _lista.value = UiState.Exito(actual.datos.filterNot { it.id == id })
        }
    }

    private fun filtrarPorTitulo(
        estado: UiState<List<Post>>,
        consulta: String
    ): UiState<List<Post>> {
        if (estado !is UiState.Exito) return estado

        val texto = consulta.trim()
        if (texto.isEmpty()) return estado

        return UiState.Exito(
            estado.datos.filter { post ->
                post.title.contains(texto, ignoreCase = true)
            }
        )
    }

    fun limpiarCreacion() {
        _creacion.value = null
    }
}
