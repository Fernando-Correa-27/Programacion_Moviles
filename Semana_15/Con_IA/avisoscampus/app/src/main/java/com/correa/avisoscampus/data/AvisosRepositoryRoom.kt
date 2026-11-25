package com.correa.avisoscampus.data

import com.correa.avisoscampus.model.Aviso
import com.correa.avisoscampus.model.Origen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AvisosRepositoryRoom(
    private val dao: AvisosDao,
    alcance: CoroutineScope
) : AvisosRepository {
    override val avisos: StateFlow<List<Aviso>> = dao.observar()
        .map { registros ->
            registros.map { Aviso(it.id, it.titulo, it.texto, Origen.valueOf(it.origen), it.hora) }
        }
        .stateIn(alcance, SharingStarted.Eagerly, emptyList())

    override fun registrar(titulo: String, texto: String, origen: Origen): Aviso {
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        // El ViewModel ejecuta esta escritura en IO. Room asigna el id antes de notificar.
        val id = dao.insertar(AvisoGuardado(titulo = titulo, texto = texto, origen = origen.name, hora = hora))
        return Aviso(Math.toIntExact(id), titulo, texto, origen, hora)
    }
}
