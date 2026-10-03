package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun DatosEntregaScreen(
    onVolver: () -> Unit,
    onConfirmarPedido: (String, String, String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf("Juan Pérez") }
    var telefono by remember { mutableStateOf("987 654 321") }
    var direccion by remember { mutableStateOf("Av. Los Olivos 123") }
    var referencia by remember { mutableStateOf("Frente al parque") }
    var metodoPago by remember { mutableStateOf("Efectivo al entregar") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text("Datos de entrega", style = MaterialTheme.typography.titleLarge)
        }

        CampoTexto("Nombre", nombre, { nombre = it })
        Spacer(Modifier.height(8.dp))
        CampoTexto("Teléfono", telefono, { telefono = it }, teclado = KeyboardType.Phone)
        Spacer(Modifier.height(8.dp))
        CampoTexto("Dirección", direccion, { direccion = it })
        Spacer(Modifier.height(8.dp))
        CampoTexto("Referencia", referencia, { referencia = it })

        Text(
            "Método de pago",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 18.dp, bottom = 4.dp)
        )

        listOf("Efectivo al entregar", "Yape", "Plin").forEach { opcion ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = metodoPago == opcion,
                    onClick = { metodoPago = opcion }
                )
                if (opcion == "Efectivo al entregar") {
                    Icon(Icons.Default.Payments, contentDescription = null, tint = VerdeBodega)
                }
                Text(opcion, modifier = Modifier.padding(start = 10.dp))
            }
        }

        Spacer(Modifier.height(12.dp))
        BotonPrimario(
            texto = "Confirmar pedido",
            onClick = { onConfirmarPedido(nombre, telefono, direccion, referencia, metodoPago) }
        )
        Spacer(Modifier.height(20.dp))
    }
}
