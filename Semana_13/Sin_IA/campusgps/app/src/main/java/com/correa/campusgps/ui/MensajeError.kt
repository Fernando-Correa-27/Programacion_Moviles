package com.correa.campusgps.ui

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.correa.campusgps.model.TipoError
import com.correa.campusgps.model.UbicacionUi

@Composable
fun MensajeError(error: UbicacionUi.Error, alReintentar: () -> Unit) {
    val contexto = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
    ) {
        Text(error.mensaje, color = MaterialTheme.colorScheme.error)
        when (error.tipo) {
            TipoError.SIN_PERMISO -> Button(onClick = {
                contexto.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", contexto.packageName, null)
                })
            }) { Text("Abrir ajustes") }
            TipoError.GPS_APAGADO, TipoError.OTRO -> Button(onClick = alReintentar) { Text("Reintentar") }
            TipoError.SIN_PLAY_SERVICES -> Unit
        }
    }
}
