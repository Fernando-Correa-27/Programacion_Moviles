package com.correa.avisoscampus.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.correa.avisoscampus.model.PermisoUi
import com.correa.avisoscampus.notificacion.Notificador
import com.correa.avisoscampus.viewmodel.AvisosViewModel

private val AzulBarra = Color(0xFF174475)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current
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
            Notificador.mostrar(contexto, aviso)
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
            }
            TarjetaAvisos("Avisos") {
                Button(onClick = {}, enabled = false) { Text("Mostrar aviso") }
            }
            TarjetaAvisos("Recordatorio") {
                Button(onClick = {}, enabled = false) { Text("Recordar en 10 s") }
                OutlinedButton(onClick = {}, enabled = false) { Text("Cancelar") }
            }
            TarjetaAvisos("Receptor de anuncios") {
                Text("Escuchar el modo avión")
                Switch(checked = false, onCheckedChange = null, enabled = false)
            }
            TarjetaAvisos("Historial") {
                Text("Sin avisos todavía")
            }
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
