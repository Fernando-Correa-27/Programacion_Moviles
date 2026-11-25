package com.correa.avisoscampus.model

enum class Origen(val texto: String) {
    BOTON("Botón"),
    RECORDATORIO("Recordatorio"),
    MODO_AVION("Modo avión")
}

enum class CanalAviso {
    AVISOS, URGENTES
}

data class Aviso(
    val id: Int,
    val titulo: String,
    val texto: String,
    val origen: Origen,
    val hora: String
) {
    val canal: CanalAviso
        get() = when (origen) {
            Origen.RECORDATORIO -> CanalAviso.URGENTES
            else -> CanalAviso.AVISOS
        }
}

sealed interface PermisoUi {
    data object SinPedir : PermisoUi
    data object Concedido : PermisoUi
    data object Denegado : PermisoUi
}
