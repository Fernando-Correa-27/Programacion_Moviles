package com.tecsup.rickandmorty.data.mapper

import com.tecsup.rickandmorty.data.local.ItemEntity
import com.tecsup.rickandmorty.data.remote.dto.ItemDto
import com.tecsup.rickandmorty.domain.model.Item

fun ItemDto.aEntity(esFavorito: Boolean = false, incluirDetalle: Boolean = false) = ItemEntity(
    id = id, titulo = name, especie = species, estado = status, imagenUrl = image,
    esFavorito = esFavorito,
    genero = if (incluirDetalle) gender else null,
    origen = if (incluirDetalle) origin.name else null,
    ubicacion = if (incluirDetalle) location.name else null,
    cantidadEpisodios = if (incluirDetalle) episode.size else null
)

fun ItemEntity.aModelo() = Item(
    id = id, titulo = titulo, subtitulo = "$especie · $estado", imagenUrl = imagenUrl,
    esFavorito = esFavorito, genero = genero, origen = origen,
    ubicacion = ubicacion, cantidadEpisodios = cantidadEpisodios
)
