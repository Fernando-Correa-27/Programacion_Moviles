package com.tecsup.rickandmorty.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RespuestaPersonajesDto(val info: InfoDto, val results: List<ItemDto>)

@Serializable
data class InfoDto(val count: Int, val pages: Int, val next: String? = null, val prev: String? = null)

@Serializable
data class ItemDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String = "",
    val gender: String,
    val origin: LugarDto,
    val location: LugarDto,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

@Serializable
data class LugarDto(val name: String, val url: String)
