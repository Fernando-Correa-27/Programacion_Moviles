package com.correa.tecsupfit.data

val filtrosClases = listOf("Hoy", "Esta semana")

val clasesFit = listOf(
    ClaseFit(
        id = 1,
        nombre = "Cross Training",
        dia = "Hoy",
        horario = "6:00 pm",
        sala = "Sala 1",
        duracionMin = 45,
        cuposDisponibles = 8,
        cuposTotales = 12,
        horariosDisponibles = listOf("6:00 pm", "7:00 pm", "8:00 pm"),
        descripcion = "Entrenamiento funcional de alta intensidad que combina fuerza, resistencia y cardio en circuito."
    ),
    ClaseFit(
        id = 2,
        nombre = "Yoga Flow",
        dia = "Hoy",
        horario = "7:00 am",
        sala = "Sala 2",
        duracionMin = 60,
        cuposDisponibles = 5,
        cuposTotales = 10,
        horariosDisponibles = listOf("7:00 am", "9:00 am", "11:00 am"),
        descripcion = "Sesión de yoga dinámico para mejorar flexibilidad, equilibrio y reducir el estrés."
    ),
    ClaseFit(
        id = 3,
        nombre = "Spinning",
        dia = "Hoy",
        horario = "8:00 pm",
        sala = "Sala 3",
        duracionMin = 45,
        cuposDisponibles = 10,
        cuposTotales = 15,
        horariosDisponibles = listOf("8:00 pm", "9:00 pm", "10:00 pm"),
        descripcion = "Clase de ciclismo indoor con música y ritmo variable para quemar calorías."
    ),
    ClaseFit(
        id = 4,
        nombre = "HIIT Intensivo",
        dia = "Esta semana",
        horario = "10:00 am",
        sala = "Sala 1",
        duracionMin = 30,
        cuposDisponibles = 12,
        cuposTotales = 16,
        horariosDisponibles = listOf("10:00 am", "11:00 am", "12:00 pm"),
        descripcion = "Rutina corta de intervalos de alta intensidad para maximizar el rendimiento en poco tiempo."
    )
)

val reservasIniciales = listOf(
    Reserva(
        id = 1,
        nombreClase = "Cross Training",
        horario = "6:00 pm",
        sala = "Sala 1",
        estado = EstadoReserva.CONFIRMADA
    ),
    Reserva(
        id = 2,
        nombreClase = "Yoga Flow",
        horario = "9:00 am",
        sala = "Sala 2",
        estado = EstadoReserva.COMPLETADA
    )
)