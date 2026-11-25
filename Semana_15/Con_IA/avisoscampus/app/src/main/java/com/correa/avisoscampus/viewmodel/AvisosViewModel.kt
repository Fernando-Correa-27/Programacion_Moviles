package com.correa.avisoscampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correa.avisoscampus.data.AvisosRepository
import com.correa.avisoscampus.model.Aviso
import com.correa.avisoscampus.model.Origen
import com.correa.avisoscampus.model.PermisoUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AvisosViewModel(private val repo: AvisosRepository) : ViewModel() {
    private val escritura = Dispatchers.IO.limitedParallelism(1)

    val avisos: StateFlow<List<Aviso>> = repo.avisos

    private val _eventos = MutableSharedFlow<Aviso>()
    val eventos: SharedFlow<Aviso> = _eventos.asSharedFlow()

    private val _permiso = MutableStateFlow<PermisoUi>(PermisoUi.SinPedir)
    val permiso: StateFlow<PermisoUi> = _permiso.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun permisoRespondido(concedido: Boolean) {
        _permiso.value = if (concedido) PermisoUi.Concedido else PermisoUi.Denegado
        if (concedido) _mensaje.value = null
    }

    private val _faltan = MutableStateFlow<Int?>(null)
    val faltan: StateFlow<Int?> = _faltan.asStateFlow()
    private var recordatorio: Job? = null

    private val _escuchandoAvion = MutableStateFlow(false)
    val escuchandoAvion: StateFlow<Boolean> = _escuchandoAvion.asStateFlow()

    private val _escuchandoCargador = MutableStateFlow(false)
    val escuchandoCargador: StateFlow<Boolean> = _escuchandoCargador.asStateFlow()

    fun avisarAhora() {
        registrarAviso("Tarea lista", "Tu entrega fue registrada", Origen.BOTON)
    }

    fun recordarEn(segundos: Int = 10) {
        if (recordatorio?.isActive == true) return
        recordatorio = viewModelScope.launch {
            try {
                for (restantes in segundos downTo 1) {
                    _faltan.value = restantes
                    delay(1000)
                }
                registrarAviso("Recordatorio", "Revisa tu laboratorio pendiente", Origen.RECORDATORIO)
            } finally {
                _faltan.value = null
            }
        }
    }

    fun cancelarRecordatorio() {
        recordatorio?.cancel()
    }

    fun cambiarEscucha(valor: Boolean) {
        _escuchandoAvion.value = valor
    }

    fun alCambiarModoAvion(activo: Boolean) {
        if (!_escuchandoAvion.value) return
        registrarAviso("Modo avión", if (activo) "Activado" else "Desactivado", Origen.MODO_AVION)
    }

    fun cambiarEscuchaCargador(valor: Boolean) {
        _escuchandoCargador.value = valor
    }

    fun alCambiarCargador(conectado: Boolean) {
        if (!_escuchandoCargador.value) return
        registrarAviso("Cargador", if (conectado) "Conectado" else "Desconectado", Origen.CARGADOR)
    }

    fun noSePudoMostrar() {
        _mensaje.value = "Sin permiso: el aviso se guardó en el historial, pero no se mostró"
    }

    private fun registrarAviso(titulo: String, texto: String, origen: Origen) {
        viewModelScope.launch {
            val aviso = withContext(escritura) { repo.registrar(titulo, texto, origen) }
            // Durante una rotación espera al nuevo colector sin repetir avisos anteriores.
            _eventos.subscriptionCount.first { it > 0 }
            _eventos.emit(aviso)
        }
    }
}
