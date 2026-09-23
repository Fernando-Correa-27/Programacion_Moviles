package com.correa.tecsupfit.data

enum class EstadoReserva {
    CONFIRMADA,
    COMPLETADA
}

data class ClaseFit(
    val id: Int,
    val nombre: String,
    val dia: String,
    val horario: String,
    val sala: String,
    val duracionMin: Int,
    val cuposDisponibles: Int,
    val cuposTotales: Int,
    val horariosDisponibles: List<String>,
    val descripcion: String
)

data class Reserva(
    val id: Int,
    val nombreClase: String,
    val horario: String,
    val sala: String,
    val estado: EstadoReserva
)