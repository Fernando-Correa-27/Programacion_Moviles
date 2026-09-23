package com.correa.actividad_semana_6.data

enum class EstadoCita {
    CONFIRMADA,
    COMPLETADA
}

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val aniosExperiencia: Int,
    val numeroResenas: Int,
    val descripcion: String
)

data class Cita(
    val id: Int,
    val nombreMedico: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val estado: EstadoCita
)