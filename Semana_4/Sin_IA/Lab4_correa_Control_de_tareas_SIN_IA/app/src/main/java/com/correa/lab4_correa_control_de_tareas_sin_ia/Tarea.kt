package com.correa.lab4_correa_control_de_tareas_sin_ia

data class Tarea(
    val id: Int,
    val texto: String,
    val completada: Boolean = false
)