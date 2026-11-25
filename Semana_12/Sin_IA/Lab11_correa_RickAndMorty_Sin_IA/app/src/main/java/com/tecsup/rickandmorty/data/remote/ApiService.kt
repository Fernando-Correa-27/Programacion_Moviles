package com.tecsup.rickandmorty.data.remote

import com.tecsup.rickandmorty.data.remote.dto.ItemDto
import com.tecsup.rickandmorty.data.remote.dto.RespuestaPersonajesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun personajes(@Query("status") estado: String = "alive"): RespuestaPersonajesDto

    @GET("character/{id}")
    suspend fun personaje(@Path("id") id: Int): ItemDto
}
