package com.correa.campusgps.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.correa.campusgps.model.UbicacionUi
import com.correa.campusgps.util.formatearDistancia
import com.correa.campusgps.viewmodel.MapaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    val lugares by vm.lugares.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val masCercano by vm.masCercano.collectAsState()
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CampusGPS") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { relleno ->
        BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(relleno)) {
            val compacto = maxHeight < 560.dp
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(if (compacto) Modifier.verticalScroll(rememberScrollState()) else Modifier)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = ::alPulsar, enabled = estado !is UbicacionUi.Buscando) {
                    Text(if (estado is UbicacionUi.Buscando) "Buscando..." else "Obtener mi ubicación")
                }
                Box(
                    modifier = (if (compacto) {
                        Modifier.fillMaxWidth().height(180.dp)
                    } else {
                        Modifier.fillMaxWidth().weight(1f)
                    }).clipToBounds()
                ) {
                    MapaUbicacion(
                        estado = estado,
                        lugares = lugares,
                        seleccionado = seleccionado,
                        masCercanoId = masCercano?.id,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                ListaLugares(
                    lugares = lugares,
                    seleccionado = seleccionado,
                    onClick = vm::seleccionar,
                    modifier = Modifier.fillMaxWidth().height(if (compacto) 160.dp else 220.dp)
                )
                OutlinedButton(
                    onClick = { masCercano?.let { vm.seleccionar(it.id) } },
                    enabled = masCercano != null
                ) {
                    Text("Ir al más cercano")
                }
                masCercano?.let { lugar ->
                    val distancia = lugares.firstOrNull { it.lugar.id == lugar.id }?.metros
                    Text("Más cercano: ${lugar.nombre}${distancia?.let { " (${formatearDistancia(it)})" } ?: ""}")
                }
                when (val actual = estado) {
                    is UbicacionUi.Lista -> Text("Latitud: ${actual.posicion.latitud}\nLongitud: ${actual.posicion.longitud}")
                    is UbicacionUi.Error -> MensajeError(actual, vm::buscar)
                    else -> Unit
                }
            }
        }
    }
}
