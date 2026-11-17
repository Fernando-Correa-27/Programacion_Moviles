package com.correa.centrotareas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.correa.centrotareas.model.CargaUi
import com.correa.centrotareas.model.PrimosUi
import com.correa.centrotareas.ui.theme.AzulCentro
import com.correa.centrotareas.ui.theme.VerdeExito
import com.correa.centrotareas.ui.theme.VerdeExitoFondo
import com.correa.centrotareas.ui.theme.VerdeExitoFondoOscuro
import com.correa.centrotareas.ui.theme.VerdeExitoOscuro
import com.correa.centrotareas.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(viewModel: TareasViewModel) {
    val mensaje by viewModel.mensaje.collectAsState()
    val progreso by viewModel.progreso.collectAsState()
    val corriendo by viewModel.corriendo.collectAsState()
    val primos by viewModel.primos.collectAsState()
    val carga by viewModel.carga.collectAsState()
    var hasta by rememberSaveable { mutableStateOf("9999999") }
    var toques by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CentroTareas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulCentro,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Tarea con progreso",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = { toques++ }) {
                        Text("Tócame: $toques")
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { viewModel.tareaLarga() },
                            enabled = !corriendo
                        ) {
                            Text("Iniciar")
                        }
                        OutlinedButton(
                            onClick = { viewModel.cancelar() },
                            enabled = corriendo
                        ) {
                            Text("Cancelar")
                        }
                    }
                    LinearProgressIndicator(
                        progress = { progreso },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "${(progreso * 100).toInt()} %",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = mensaje, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Contar números primos",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = hasta,
                        onValueChange = { nuevo ->
                            if (nuevo.length <= 7 && nuevo.all { it.isDigit() }) {
                                hasta = nuevo
                            }
                        },
                        label = { Text("Hasta el número") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                val numero = hasta.toIntOrNull()
                                if (numero != null && numero >= 2) viewModel.contar(numero)
                            },
                            enabled = primos !is PrimosUi.Calculando
                        ) {
                            Text("Contar")
                        }
                        OutlinedButton(
                            onClick = { viewModel.cancelarPrimos() },
                            enabled = primos is PrimosUi.Calculando
                        ) {
                            Text("Cancelar")
                        }
                    }
                    when (val estado = primos) {
                        PrimosUi.Inicial -> Unit
                        PrimosUi.Calculando -> Text(
                            text = "Calculando...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        is PrimosUi.Listo -> RecuadroExito(
                            "Primos hasta $hasta: ${estado.cantidad}\n" +
                                "Tiempo: ${estado.milisegundos} ms"
                        )
                        PrimosUi.Cancelado -> Text(
                            text = "Cancelado",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Dos cargas",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.cargarUnaPorUna() },
                            enabled = carga !is CargaUi.Cargando
                        ) {
                            Text("Una por una")
                        }
                        Button(
                            onClick = { viewModel.cargarALaVez() },
                            enabled = carga !is CargaUi.Cargando
                        ) {
                            Text("A la vez")
                        }
                    }
                    when (val estado = carga) {
                        CargaUi.Inicial -> Unit
                        CargaUi.Cargando -> Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(36.dp))
                        }
                        is CargaUi.Exito -> RecuadroExito(
                            "Modo: ${estado.modo}\n" +
                                "Perfil: ${estado.perfil}\n" +
                                "Pedidos: ${estado.pedidos}\n" +
                                "Tiempo: ${estado.milisegundos} ms"
                        )
                        is CargaUi.Error -> Unit
                    }
                }
            }
        }
    }
}

@Composable
private fun RecuadroExito(texto: String) {
    val oscuro = isSystemInDarkTheme()
    val fondo = if (oscuro) VerdeExitoFondoOscuro else VerdeExitoFondo
    val color = if (oscuro) VerdeExitoOscuro else VerdeExito
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(fondo)
            .padding(12.dp)
    ) {
        Text(
            text = texto,
            modifier = Modifier.fillMaxWidth(),
            color = color,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
