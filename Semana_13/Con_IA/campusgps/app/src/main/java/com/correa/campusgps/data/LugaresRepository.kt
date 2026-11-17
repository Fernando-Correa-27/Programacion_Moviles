package com.correa.campusgps.data

import com.correa.campusgps.model.Lugar

interface LugaresRepository {
    fun obtenerLugares(): List<Lugar>
}
