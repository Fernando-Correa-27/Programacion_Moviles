package com.correa.avisoscampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correa.avisoscampus.data.AvisosRepository
import com.correa.avisoscampus.model.Aviso
import com.correa.avisoscampus.model.Origen
import com.correa.avisoscampus.model.PermisoUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AvisosViewModel(private val repo: AvisosRepository) : ViewModel() {
    val avisos: StateFlow<List<Aviso>> = repo.avisos

    private val _eventos = MutableSharedFlow<Aviso>()
    val eventos: SharedFlow<Aviso> = _eventos.asSharedFlow()

    private val _permiso = MutableStateFlow<PermisoUi>(PermisoUi.SinPedir)
    val permiso: StateFlow<PermisoUi> = _permiso.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun permisoRespondido(concedido: Boolean) {
        _permiso.value = if (concedido) PermisoUi.Concedido else PermisoUi.Denegado
    }

    fun avisarAhora() {
        registrarAviso("Tarea lista", "Tu entrega fue registrada", Origen.BOTON)
    }

    private fun registrarAviso(titulo: String, texto: String, origen: Origen) {
        viewModelScope.launch {
            val aviso = repo.registrar(titulo, texto, origen)
            // Durante una rotación espera al nuevo colector sin repetir avisos anteriores.
            _eventos.subscriptionCount.first { it > 0 }
            _eventos.emit(aviso)
        }
    }
}
