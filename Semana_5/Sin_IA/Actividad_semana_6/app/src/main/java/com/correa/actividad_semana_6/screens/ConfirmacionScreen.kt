package com.correa.actividad_semana_6.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.correa.actividad_semana_6.components.BotonSecundario
import com.correa.actividad_semana_6.data.fechasDisponibles
import com.correa.actividad_semana_6.data.horasDisponibles
import com.correa.actividad_semana_6.data.medicos
import com.correa.actividad_semana_6.ui.theme.VerdeExito
import com.correa.actividad_semana_6.ui.theme.VerdeMenta

@Composable
fun ConfirmacionScreen(
    medicoId: Int,
    fechaIdx: Int,
    horaIdx: Int,
    onVerMisCitas: () -> Unit,
    onVolverAlInicio: () -> Unit
) {
    val medico = medicos.first { it.id == medicoId }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = VerdeMenta,
                modifier = Modifier.size(96.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = VerdeExito,
                        modifier = Modifier.size(52.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "¡Cita agendada!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = medico.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${fechasDisponibles[fechaIdx]} • ${horasDisponibles[horaIdx]}",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(28.dp))

            BotonSecundario(
                texto = "Ver mis citas",
                onClick = onVerMisCitas
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onVolverAlInicio) {
                Text(
                    text = "Volver al inicio",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}