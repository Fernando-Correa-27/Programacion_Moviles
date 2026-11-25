package com.tecsup.rickandmorty.data.repository

import com.tecsup.rickandmorty.data.local.ItemDao
import com.tecsup.rickandmorty.data.mapper.aEntity
import com.tecsup.rickandmorty.data.mapper.aModelo
import com.tecsup.rickandmorty.data.remote.ApiService
import com.tecsup.rickandmorty.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

class ItemRepositoryImpl(
    private val api: ApiService,
    private val dao: ItemDao
) : ItemRepository {
    override val items: Flow<List<Item>> = dao.todos().map { lista -> lista.map { it.aModelo() } }
    override val favoritos: Flow<List<Item>> = dao.favoritos().map { lista -> lista.map { it.aModelo() } }

    override suspend fun refrescar(): Result<Int> = try {
        val idsFavoritos = dao.idsFavoritos().toSet()
        val personajes = try {
            api.personajes().results
        } catch (error: HttpException) {
            if (error.code() == 404) emptyList() else throw error
        }
        val entidades = personajes.map { personaje ->
            val detalleGuardado = dao.porId(personaje.id)
            personaje.aEntity(esFavorito = personaje.id in idsFavoritos).copy(
                genero = detalleGuardado?.genero,
                origen = detalleGuardado?.origen,
                ubicacion = detalleGuardado?.ubicacion,
                cantidadEpisodios = detalleGuardado?.cantidadEpisodios
            )
        }
        if (personajes.isEmpty()) dao.borrarTodo() else dao.guardarTodos(entidades)
        Result.success(entidades.size)
    } catch (error: Exception) {
        Result.failure(error)
    }

    override suspend fun obtenerDetalle(id: Int): Result<Item> = runCatching {
        val detalle = api.personaje(id)
        val esFavorito = dao.porId(id)?.esFavorito ?: false
        detalle.aEntity(esFavorito = esFavorito, incluirDetalle = true).also { dao.guardar(it) }.aModelo()
    }

    override suspend fun alternarFavorito(id: Int) = dao.alternarFavorito(id)
    override suspend fun cantidadGuardada() = dao.contar()
    override suspend fun cantidadFavoritos() = dao.contarFavoritos()
    override suspend fun limpiarCache() = dao.borrarTodo()
}
