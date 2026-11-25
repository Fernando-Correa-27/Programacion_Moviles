package com.correa.avisoscampus.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "avisos")
data class AvisoGuardado(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val texto: String,
    val origen: String,
    val hora: String
)

@Dao
interface AvisosDao {
    @Query("SELECT * FROM avisos ORDER BY id DESC")
    fun observar(): Flow<List<AvisoGuardado>>

    @Insert
    fun insertar(aviso: AvisoGuardado): Long
}

@Database(entities = [AvisoGuardado::class], version = 1, exportSchema = false)
abstract class AvisosDatabase : RoomDatabase() {
    abstract fun avisosDao(): AvisosDao
}
