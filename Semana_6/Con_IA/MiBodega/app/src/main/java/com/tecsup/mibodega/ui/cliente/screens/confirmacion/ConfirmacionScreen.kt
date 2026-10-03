package com.tecsup.mibodega.ui.cliente.screens.confirmacion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.theme.GrisBorde
import com.tecsup.mibodega.ui.theme.RojoPrecio
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun ConfirmacionScreen(
    total: Double,
    nombre: String,
    direccion: String,
    referencia: String,
    onVerPedido: () -> Unit,
    onVolverInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = VerdeBodega,
            modifier = Modifier.height(82.dp)
        )
        Text(
            "¡Pedido realizado!",
            color = VerdeBodega,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            "Tu pedido está siendo preparado y será entregado pronto.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, GrisBorde)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Pedido #1024", fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total")
                    Text("S/ %.2f".format(total), color = RojoPrecio, fontWeight = FontWeight.Bold)
                }
                Text("Entrega a", modifier = Modifier.padding(top = 10.dp))
                Text(nombre, fontWeight = FontWeight.Medium)
                Text(direccion)
                Text("($referencia)")
            }
        }

        Spacer(Modifier.weight(1f))
        OutlinedButton(
            onClick = onVerPedido,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(Icons.Default.Phone, contentDescription = null, tint = VerdeBodega)
            Text("Ver estado del pedido", color = VerdeBodega, modifier = Modifier.padding(start = 8.dp))
        }
        OutlinedButton(
            onClick = onVolverInicio,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Volver al inicio", color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
