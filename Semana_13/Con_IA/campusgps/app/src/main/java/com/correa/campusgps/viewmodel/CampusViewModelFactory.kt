package com.correa.campusgps.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.correa.campusgps.data.LugaresRepositoryLocal
import com.correa.campusgps.data.PreferenciasRepositoryDataStore
import com.correa.campusgps.data.UbicacionRepositoryGoogle

class CampusViewModelFactory(private val contexto: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MapaViewModel(
        ubicacionRepo = UbicacionRepositoryGoogle(contexto.applicationContext),
        lugaresRepo = LugaresRepositoryLocal(),
        preferenciasRepo = PreferenciasRepositoryDataStore(contexto.applicationContext)
    ) as T
}
