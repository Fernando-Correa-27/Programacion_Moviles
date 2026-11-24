package com.correa.murocampus.data.remote

import com.correa.murocampus.model.Usuario
import retrofit2.http.GET

interface UsuarioApi {

    @GET("users")
    suspend fun listar(): List<Usuario>
}
