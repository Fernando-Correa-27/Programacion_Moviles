package com.correa.avisoscampus.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.correa.avisoscampus.model.PermisoUi
import com.correa.avisoscampus.notificacion.Notificador
import com.correa.avisoscampus.receiver.CargadorReceiver
import com.correa.avisoscampus.receiver.ModoAvionReceiver
import com.correa.avisoscampus.viewmodel.AvisosViewModel

private val AzulBarra = Color(0xFF174475)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current
    val avisos by vm.avisos.collectAsState()
    val escuchando by vm.escuchandoAvion.collectAsState()
    val escuchandoCargador by vm.escuchandoCargador.collectAsState()
    val faltan by vm.faltan.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val permiso by vm.permiso.collectAsState()
    val solicitarPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
        vm::permisoRespondido
    )
    LaunchedEffect(Unit) {
        if (Notificador.tienePermiso(contexto)) vm.permisoRespondido(true)
    }
    LaunchedEffect(vm) {
        vm.eventos.collect { aviso ->
            if (!Notificador.mostrar(contexto, aviso)) vm.noSePudoMostrar()
        }
    }

    DisposableEffect(contexto, escuchando) {
        val receptor = if (escuchando) ModoAvionReceiver(vm::alCambiarModoAvion) else null
        if (receptor != null) {
            ContextCompat.registerReceiver(
                contexto,
                receptor,
                IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
        onDispose {
            if (receptor != null) contexto.unregisterReceiver(receptor)
        }
    }

    DisposableEffect(contexto, escuchandoCargador) {
        val receptor = if (escuchandoCargador) CargadorReceiver(vm::alCambiarCargador) else null
        if (receptor != null) {
            val filtro = IntentFilter(Intent.ACTION_POWER_CONNECTED).apply {
                addAction(Intent.ACTION_POWER_DISCONNECTED)
            }
            ContextCompat.registerReceiver(
                contexto, receptor, filtro, ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
        onDispose {
            if (receptor != null) contexto.unregisterReceiver(receptor)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AvisosCampus") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulBarra,
                    titleContentColor = Color.White
                )
            )
        }
    ) { espacioBarras ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioBarras)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaAvisos("Permiso de notificaciones") {
                EtiquetaPermiso(permiso)
                Button(onClick = {
                    if (Notificador.tienePermiso(contexto)) {
                        vm.permisoRespondido(true)
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        solicitarPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }) { Text("Permitir notificaciones") }
                mensaje?.let { error ->
                    val colores = MaterialTheme.colorScheme
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .background(colores.errorContainer, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(error, color = colores.onErrorContainer)
                        OutlinedButton(onClick = {
                            val ajustes = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                .putExtra(Settings.EXTRA_APP_PACKAGE, contexto.packageName)
                            contexto.startActivity(ajustes)
                        }) { Text("Abrir ajustes") }
                    }
                }
            }
            TarjetaAvisos("Avisos") {
                Button(onClick = vm::avisarAhora) { Text("Mostrar aviso") }
            }
            TarjetaAvisos("Recordatorio") {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { vm.recordarEn() }, enabled = faltan == null) {
                        Text("Recordar en 10 s")
                    }
                    OutlinedButton(onClick = vm::cancelarRecordatorio, enabled = faltan != null) {
                        Text("Cancelar")
                    }
                }
                faltan?.let { Text("Faltan $it s") }
            }
            TarjetaAvisos("Receptor de anuncios") {
                InterruptorEscucha("Escuchar el modo avión", escuchando, vm::cambiarEscucha)
                InterruptorEscucha(
                    "Escuchar el cargador", escuchandoCargador, vm::cambiarEscuchaCargador
                )
            }
            HistorialAvisos(avisos)
        }
    }
}

@Composable
internal fun TarjetaAvisos(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    val esquema = MaterialTheme.colorScheme
    val azulTitulo = if (androidx.compose.foundation.isSystemInDarkTheme()) {
        Color(0xFFA7CBFF)
    } else {
        AzulBarra
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = esquema.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                titulo,
                modifier = Modifier.semantics { heading() },
                style = MaterialTheme.typography.titleMedium,
                color = azulTitulo
            )
            contenido()
        }
    }
}

@Composable
private fun EtiquetaPermiso(permiso: PermisoUi) {
    val (texto, fondo, tinta) = when (permiso) {
        PermisoUi.SinPedir -> Triple("Sin pedir", Color(0xFFE2E8F0), Color(0xFF334155))
        PermisoUi.Concedido -> Triple("Permitido", Color(0xFFDCFCE7), Color(0xFF166534))
        PermisoUi.Denegado -> Triple("Denegado", Color(0xFFFEE2E2), Color(0xFF991B1B))
    }
    Text(
        texto,
        modifier = Modifier.background(fondo, RoundedCornerShape(8.dp)).padding(8.dp, 4.dp),
        style = MaterialTheme.typography.labelMedium,
        color = tinta
    )
}

@Composable
private fun InterruptorEscucha(texto: String, activo: Boolean, cambiar: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().toggleable(activo, role = Role.Switch, onValueChange = cambiar),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(texto, modifier = Modifier.weight(1f))
        Switch(checked = activo, onCheckedChange = null)
    }
}
