package com.correa.registro_de_notas_sin_ia.data

data class Curso(
    val nombre: String,
    val peso: Float
)

val cursos = listOf(
    Curso("Fundamentos de Programación", 0.20f),
    Curso("Programación Orientada a Objetos", 0.25f),
    Curso("Programación en Móviles", 0.30f),
    Curso("Base de Datos", 0.25f)
)