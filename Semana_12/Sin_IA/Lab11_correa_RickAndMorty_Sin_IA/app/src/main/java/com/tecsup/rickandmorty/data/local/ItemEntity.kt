package com.tecsup.rickandmorty.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personajes")
data class ItemEntity(
    @PrimaryKey val id: Int,
    val titulo: String,
    val especie: String,
    val estado: String,
    val imagenUrl: String,
    val esFavorito: Boolean = false,
    val genero: String? = null,
    val origen: String? = null,
    val ubicacion: String? = null,
    val cantidadEpisodios: Int? = null
)
