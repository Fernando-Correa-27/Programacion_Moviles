package com.tecsup.mibodega.ui.cliente.screens.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.componentes.BarraNavegacionCliente
import com.tecsup.mibodega.ui.cliente.componentes.SeccionCliente
import com.tecsup.mibodega.ui.theme.VerdeBodega

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    nombre: String,
    telefono: String,
    direccion: String,
    referencia: String,
    onSeccionSeleccionada: (SeccionCliente) -> Unit,
    modoOscuro: Boolean = false,
    onModoOscuroCambia: (Boolean) -> Unit = {}
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil") }) },
        bottomBar = {
            BarraNavegacionCliente(SeccionCliente.PERFIL, onSeccionSeleccionada)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.height(72.dp)
            )
            Text(nombre, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))
            Spacer(Modifier.height(24.dp))
            DatoPerfil("Teléfono", telefono)
            DatoPerfil("Dirección", direccion)
            DatoPerfil("Referencia", referencia)
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Modo oscuro", fontWeight = FontWeight.Medium)
                Switch(checked = modoOscuro, onCheckedChange = onModoOscuroCambia)
            }
        }
    }
}

@Composable
private fun DatoPerfil(etiqueta: String, valor: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        Text(etiqueta, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(valor, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 3.dp))
        HorizontalDivider(modifier = Modifier.padding(top = 10.dp))
    }
}
