package com.correa.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correa.campusgps.data.LugaresRepository
import com.correa.campusgps.data.UbicacionRepository
import com.correa.campusgps.model.LugarConDistancia
import com.correa.campusgps.model.Lugar
import com.correa.campusgps.model.UbicacionUi
import com.correa.campusgps.model.TipoError
import com.correa.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    val masCercano: StateFlow<Lugar?> = lugares
        .map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun buscar() {
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

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
    }

    private fun actualizarDistancias(posicion: com.correa.campusgps.model.Posicion) {
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
