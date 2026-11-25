package com.tecsup.rickandmorty.domain.model

data class Item(
    val id: Int,
    val titulo: String,
    val subtitulo: String,
    val imagenUrl: String,
    val esFavorito: Boolean,
    val genero: String? = null,
    val origen: String? = null,
    val ubicacion: String? = null,
    val cantidadEpisodios: Int? = null
)
