package com.correa.centrotareas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correa.centrotareas.data.TareasRepository
import com.correa.centrotareas.model.CargaUi
import com.correa.centrotareas.model.PrimosUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {

    // ----- Tarea con progreso (pasos 1 a 4)
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    private var trabajo: Job? = null
    private val _corriendo = MutableStateFlow(false)
    val corriendo: StateFlow<Boolean> = _corriendo.asStateFlow()

    fun tareaLarga() {
        if (_corriendo.value) return
        _corriendo.value = true
        trabajo = viewModelScope.launch {
            _progreso.value = 0f
            _mensaje.value = "Trabajando..."
            try {
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                repo.tareaConProgreso().collect { valor ->
                    _progreso.value = valor
                }
                Log.d("HILO", "Despues: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                _corriendo.value = false
            }
        }
    }

    fun cancelar() {
        trabajo?.cancel()
        _mensaje.value = "Cancelado"
    }

    // ----- Contar primos (paso 5)
    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos: StateFlow<PrimosUi> = _primos.asStateFlow()

    private var trabajoPrimos: Job? = null

    fun contar(hasta: Int) {
        if (hasta < 2 || trabajoPrimos?.isActive == true) return
        trabajoPrimos = viewModelScope.launch {
            _primos.value = PrimosUi.Calculando
            val inicio = System.currentTimeMillis()
            try {
                val cantidad = repo.contarPrimos(hasta)
                _primos.value = PrimosUi.Listo(
                    cantidad = cantidad,
                    milisegundos = System.currentTimeMillis() - inicio
                )
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                throw e
            }
        }
    }

    fun cancelarPrimos() {
        trabajoPrimos?.cancel()
    }

    // ----- Dos cargas (pasos 6 y 7)
    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga: StateFlow<CargaUi> = _carga.asStateFlow()

    private var trabajoCarga: Job? = null

    fun cargarUnaPorUna() {
        iniciarCarga("Una por una") {
            val perfil = repo.cargarPerfil()
            val pedidos = repo.cargarPedidos(_simularError.value)
            perfil to pedidos
        }
    }

    fun cargarALaVez() {
        iniciarCarga("A la vez") {
            coroutineScope {
                val perfil = async { repo.cargarPerfil() }
                val pedidos = async { repo.cargarPedidos(_simularError.value) }
                perfil.await() to pedidos.await()
            }
        }
    }

    private val _simularError = MutableStateFlow(false)
    val simularError: StateFlow<Boolean> = _simularError.asStateFlow()

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }

    private fun iniciarCarga(
        modo: String,
        bloque: suspend () -> Pair<String, List<String>>
    ) {
        if (_carga.value is CargaUi.Cargando || trabajoCarga?.isActive == true) return
        trabajoCarga = viewModelScope.launch {
            _carga.value = CargaUi.Cargando
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = bloque()
                _carga.value = CargaUi.Exito(
                    modo = modo,
                    perfil = perfil,
                    pedidos = pedidos.size,
                    milisegundos = System.currentTimeMillis() - inicio
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _carga.value = CargaUi.Error(
                    "No se pudo cargar: " + (e.message ?: "error desconocido")
                )
            }
        }
    }
}
