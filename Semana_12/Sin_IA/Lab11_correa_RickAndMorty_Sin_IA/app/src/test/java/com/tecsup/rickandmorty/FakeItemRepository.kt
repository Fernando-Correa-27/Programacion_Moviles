package com.tecsup.rickandmorty

import com.tecsup.rickandmorty.data.repository.ItemRepository
import com.tecsup.rickandmorty.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeItemRepository(
    private val resultadoRefresco: Result<Int>,
    private val cantidadGuardada: Int,
    listaInicial: List<Item>
) : ItemRepository {
    private val lista = MutableStateFlow(listaInicial)
    override val items: Flow<List<Item>> = lista
    override val favoritos: Flow<List<Item>> = MutableStateFlow(listaInicial.filter { it.esFavorito })

    override suspend fun refrescar(): Result<Int> = resultadoRefresco
    override suspend fun obtenerDetalle(id: Int): Result<Item> =
        lista.value.firstOrNull { it.id == id }?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException("No existe el personaje"))
    override suspend fun alternarFavorito(id: Int) {
        lista.value = lista.value.map { if (it.id == id) it.copy(esFavorito = !it.esFavorito) else it }
    }
    override suspend fun cantidadGuardada(): Int = cantidadGuardada
    override suspend fun cantidadFavoritos(): Int = lista.value.count { it.esFavorito }
    override suspend fun limpiarCache() { lista.value = emptyList() }
}
