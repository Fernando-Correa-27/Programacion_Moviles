package com.correa.actividad_semana_6.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.correa.actividad_semana_6.components.BotonPrimario
import com.correa.actividad_semana_6.components.ChipSeleccion
import com.correa.actividad_semana_6.data.fechasDisponibles
import com.correa.actividad_semana_6.data.horasDisponibles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarScreen(
    medicoId: Int,
    onBack: () -> Unit,
    onConfirmar: (medicoId: Int, fechaIdx: Int, horaIdx: Int) -> Unit
) {
    var fechaIdx by remember { mutableIntStateOf(0) }
    var horaIdx by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Agendar cita",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Selecciona fecha",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                fechasDisponibles.forEachIndexed { index, fecha ->
                    ChipSeleccion(
                        texto = fecha,
                        seleccionado = fechaIdx == index,
                        onClick = { fechaIdx = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "Selecciona hora",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                horasDisponibles.forEachIndexed { index, hora ->
                    ChipSeleccion(
                        texto = hora,
                        seleccionado = horaIdx == index,
                        onClick = { horaIdx = index }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            BotonPrimario(
                texto = "Confirmar cita",
                onClick = { onConfirmar(medicoId, fechaIdx, horaIdx) }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}