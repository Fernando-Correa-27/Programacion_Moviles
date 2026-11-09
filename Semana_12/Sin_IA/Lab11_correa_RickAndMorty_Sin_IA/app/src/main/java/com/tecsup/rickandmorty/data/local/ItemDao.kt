package com.tecsup.rickandmorty.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM personajes ORDER BY titulo") fun todos(): Flow<List<ItemEntity>>
    @Query("SELECT * FROM personajes WHERE esFavorito = 1 ORDER BY titulo") fun favoritos(): Flow<List<ItemEntity>>
    @Query("SELECT id FROM personajes WHERE esFavorito = 1") suspend fun idsFavoritos(): List<Int>
    @Query("SELECT COUNT(*) FROM personajes") suspend fun contar(): Int
    @Query("SELECT COUNT(*) FROM personajes WHERE esFavorito = 1") suspend fun contarFavoritos(): Int
    @Query("SELECT * FROM personajes WHERE id = :id LIMIT 1") suspend fun porId(id: Int): ItemEntity?
    @Upsert suspend fun guardarTodos(items: List<ItemEntity>)
    @Upsert suspend fun guardar(item: ItemEntity)
    @Query("UPDATE personajes SET esFavorito = NOT esFavorito WHERE id = :id") suspend fun alternarFavorito(id: Int)
    @Query("DELETE FROM personajes") suspend fun borrarTodo()
}
