package com.correa.campusgps.data

import kotlinx.coroutines.flow.Flow

/** Preferencias simples de la app; el ViewModel solo conoce esta interfaz. */
interface PreferenciasRepository {
    val ultimoLugarId: Flow<Int?>
    suspend fun guardarUltimoLugar(id: Int)
}
