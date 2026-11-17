package com.correa.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correa.campusgps.data.LugaresRepository
import com.correa.campusgps.data.UbicacionRepository
import com.correa.campusgps.model.LugarConDistancia
import com.correa.campusgps.model.Lugar
import com.correa.campusgps.model.Posicion
import com.correa.campusgps.model.UbicacionUi
import com.correa.campusgps.model.TipoError
import com.correa.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    lugaresRepo: LugaresRepository
) : ViewModel() {
    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    private val _lugares = MutableStateFlow(lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) })
    val lugares: StateFlow<List<LugarConDistancia>> = _lugares.asStateFlow()

    private val _categoriaFiltro = MutableStateFlow<String?>(null)
    val categoriaFiltro: StateFlow<String?> = _categoriaFiltro.asStateFlow()

    /** Categorías derivadas de los lugares; la vista no las escribe a mano. */
    val categorias: StateFlow<List<String>> = _lugares
        .map { lista -> lista.map { it.lugar.categoria }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** Lugares que pasan el filtro de categoría; alimentan mapa, lista y "más cercano". */
    val lugaresVisibles: StateFlow<List<LugarConDistancia>> = combine(_lugares, _categoriaFiltro) { lista, categoria ->
        if (categoria == null) lista else lista.filter { it.lugar.categoria == categoria }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    private val _radioMetros = MutableStateFlow<Int?>(null)
    val radioMetros: StateFlow<Int?> = _radioMetros.asStateFlow()

    /** Ids de lugares con distancia calculada dentro del radio elegido. */
    val idsDentroDelRadio: StateFlow<Set<Int>> = combine(_lugares, _radioMetros) { lista, radio ->
        if (radio == null) emptySet()
        else lista.filter { it.metros != null && it.metros <= radio }
            .map { it.lugar.id }
            .toSet()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    val masCercano: StateFlow<Lugar?> = lugaresVisibles
        .map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun buscar() {
        _seleccionado.value = null
        if (!ubicacionRepo.playServicesDisponible()) {
            _estado.value = UbicacionUi.Error(
                "Este dispositivo no tiene Google Play Services",
                TipoError.SIN_PLAY_SERVICES
            )
            return
        }
        _estado.value = UbicacionUi.Buscando
        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess {
                    _estado.value = UbicacionUi.Lista(it)
                    actualizarDistancias(it)
                }
                .onFailure { _estado.value = UbicacionUi.Error(mensajeDe(it), tipoDe(it)) }
        }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            "Sin permiso de ubicación no puedo mostrar dónde estás",
            TipoError.SIN_PERMISO
        )
    }

    fun filtrarPorCategoria(categoria: String?) {
        _categoriaFiltro.value = categoria
        val idActual = _seleccionado.value ?: return
        val sigueVisible = _lugares.value.any {
            it.lugar.id == idActual && (categoria == null || it.lugar.categoria == categoria)
        }
        if (!sigueVisible) _seleccionado.value = null
    }

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
    }

    fun elegirRadio(metros: Int?) {
        _radioMetros.value = metros
    }

    private fun actualizarDistancias(posicion: Posicion) {
        _lugares.value = _lugares.value
            .map { LugarConDistancia(it.lugar, distanciaMetros(posicion, it.lugar)) }
            .sortedBy { it.metros }
    }

    private fun tipoDe(error: Throwable) = when (error) {
        is SecurityException -> TipoError.SIN_PERMISO
        is IllegalStateException -> TipoError.GPS_APAGADO
        else -> TipoError.OTRO
    }

    private fun mensajeDe(error: Throwable) = when (tipoDe(error)) {
        TipoError.SIN_PERMISO -> "Falta el permiso de ubicación"
        TipoError.GPS_APAGADO -> "Activa el GPS e intenta de nuevo"
        else -> error.message ?: "No se pudo obtener la ubicación"
    }
}
