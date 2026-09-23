package com.correa.actividad_semana_6.data

val especialidades = listOf(
    "Cardiología",
    "Pediatría",
    "Neurología",
    "Dermatología"
)

val fechasDisponibles = listOf(
    "Jue 26",
    "Vie 27",
    "Sáb 28"
)

val horasDisponibles = listOf(
    "9:00 am",
    "10:30 am",
    "3:00 pm"
)

val medicos = listOf(
    Medico(
        id = 1,
        nombre = "Dra. Ana Torres",
        especialidad = "Cardiología",
        calificacion = 4.9,
        aniosExperiencia = 12,
        numeroResenas = 328,
        descripcion = "Especialista en cardiología con amplia experiencia en diagnóstico y tratamiento de enfermedades cardiovasculares."
    ),
    Medico(
        id = 2,
        nombre = "Dr. Carlos Mendoza",
        especialidad = "Pediatría",
        calificacion = 4.7,
        aniosExperiencia = 8,
        numeroResenas = 210,
        descripcion = "Pediatra dedicado al cuidado de la salud de niños y adolescentes, con enfoque en prevención y seguimiento."
    ),
    Medico(
        id = 3,
        nombre = "Dra. Laura Ríos",
        especialidad = "Cardiología",
        calificacion = 4.8,
        aniosExperiencia = 10,
        numeroResenas = 254,
        descripcion = "Cardióloga especializada en ecocardiografía y atención de pacientes con riesgo cardiovascular."
    ),
    Medico(
        id = 4,
        nombre = "Dr. Luis Ramírez",
        especialidad = "Neurología",
        calificacion = 4.6,
        aniosExperiencia = 15,
        numeroResenas = 180,
        descripcion = "Neurólogo con experiencia en trastornos del sistema nervioso y epilepsia en pacientes adultos."
    )
)

val citasIniciales = listOf(
    Cita(
        id = 1,
        nombreMedico = "Dra. Ana Torres",
        especialidad = "Cardiología",
        fecha = "Mar 12",
        hora = "9:00 am",
        estado = EstadoCita.CONFIRMADA
    ),
    Cita(
        id = 2,
        nombreMedico = "Dr. Luis Ramírez",
        especialidad = "Neurología",
        fecha = "Mar 05",
        hora = "11:00 am",
        estado = EstadoCita.COMPLETADA
    )
)