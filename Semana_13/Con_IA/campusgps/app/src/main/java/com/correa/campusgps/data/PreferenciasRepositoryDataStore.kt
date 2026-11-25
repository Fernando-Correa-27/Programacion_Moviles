package com.correa.campusgps.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.preferenciasDataStore by preferencesDataStore(name = "campusgps_preferencias")

class PreferenciasRepositoryDataStore(contexto: Context) : PreferenciasRepository {
    private val contextoApp = contexto.applicationContext
    private val claveUltimoLugar = intPreferencesKey("ultimo_lugar_id")

    override val ultimoLugarId: Flow<Int?> = contextoApp.preferenciasDataStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { preferencias -> preferencias[claveUltimoLugar] }

    override suspend fun guardarUltimoLugar(id: Int) {
        contextoApp.preferenciasDataStore.edit { preferencias ->
            preferencias[claveUltimoLugar] = id
        }
    }
}
