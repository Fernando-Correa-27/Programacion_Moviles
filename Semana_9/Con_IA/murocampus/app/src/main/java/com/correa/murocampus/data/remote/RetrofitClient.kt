package com.correa.murocampus.data.remote

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    // Se agrega antes del logger para que el encabezado aparezca en Logcat.
    private val encabezadoAceptacion = Interceptor { cadena ->
        val peticion = cadena.request()
            .newBuilder()
            .header("Accept", "application/json")
            .build()
        cadena.proceed(peticion)
    }

    private val cliente = OkHttpClient.Builder()
        .addInterceptor(encabezadoAceptacion)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(cliente)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val api: PostApi by lazy { retrofit.create(PostApi::class.java) }

    val usuarioApi: UsuarioApi by lazy { retrofit.create(UsuarioApi::class.java) }
}
