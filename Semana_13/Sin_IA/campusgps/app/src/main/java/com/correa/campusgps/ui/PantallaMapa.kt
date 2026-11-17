package com.correa.campusgps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.correa.campusgps.model.UbicacionUi
import com.correa.campusgps.viewmodel.MapaViewModel

@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = vm::buscar, enabled = estado !is UbicacionUi.Buscando) {
            Text(if (estado is UbicacionUi.Buscando) "Buscando..." else "Obtener mi ubicación")
        }
        when (val actual = estado) {
            is UbicacionUi.Lista -> Text("Latitud: ${actual.posicion.latitud}\nLongitud: ${actual.posicion.longitud}")
            is UbicacionUi.Error -> Text(actual.mensaje)
            else -> Unit
        }
    }
}
