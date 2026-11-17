package com.correa.campusgps.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.correa.campusgps.model.UbicacionUi
import com.correa.campusgps.viewmodel.MapaViewModel
import androidx.core.content.ContextCompat

@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    val lugares by vm.lugares.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val contexto = LocalContext.current
    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado.values.any { it }) vm.buscar() else vm.permisoDenegado()
    }
    fun alPulsar() {
        val permisoConcedido = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).any { ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED }
        if (permisoConcedido) vm.buscar() else lanzador.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = ::alPulsar, enabled = estado !is UbicacionUi.Buscando) {
            Text(if (estado is UbicacionUi.Buscando) "Buscando..." else "Obtener mi ubicación")
        }
        MapaUbicacion(
            estado = estado,
            lugares = lugares,
            seleccionado = seleccionado,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        ListaLugares(
            lugares = lugares,
            seleccionado = seleccionado,
            onClick = vm::seleccionar,
            modifier = Modifier.fillMaxWidth().height(220.dp)
        )
        when (val actual = estado) {
            is UbicacionUi.Lista -> Text("Latitud: ${actual.posicion.latitud}\nLongitud: ${actual.posicion.longitud}")
            is UbicacionUi.Error -> Text(actual.mensaje)
            else -> Unit
        }
    }
}
