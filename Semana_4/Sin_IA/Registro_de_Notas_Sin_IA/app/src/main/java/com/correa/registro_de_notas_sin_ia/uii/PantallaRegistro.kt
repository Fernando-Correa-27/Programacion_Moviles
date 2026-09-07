package com.correa.registro_de_notas_sin_ia.uii

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.correa.registro_de_notas_sin_ia.data.cursos
import com.correa.registro_de_notas_sin_ia.data.calcularPromedioPonderado
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import kotlin.math.roundToInt
@Composable
fun PantallaRegistro(
    modifier: Modifier = Modifier
) {

    var notaFundamentos by remember {
        mutableStateOf(0f)
    }

    var notaPOO by remember {
        mutableStateOf(0f)
    }

    var notaMoviles by remember {
        mutableStateOf(0f)
    }

    var notaBD by remember {
        mutableStateOf(0f)
    }

    var redondearPromedio by remember {
        mutableStateOf(false)
    }

    var notasConfirmadas by remember {
        mutableStateOf(false)
    }

    var mostrarResultado by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            BarraSuperior()
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CursoItem(
                nombre = cursos[0].nombre,
                peso = cursos[0].peso,
                nota = notaFundamentos,
                onNotaChange = {
                    notaFundamentos = it
                }
            )

            CursoItem(
                nombre = cursos[1].nombre,
                peso = cursos[1].peso,
                nota = notaPOO,
                onNotaChange = {
                    notaPOO = it
                }
            )

            CursoItem(
                nombre = cursos[2].nombre,
                peso = cursos[2].peso,
                nota = notaMoviles,
                onNotaChange = {
                    notaMoviles = it
                }
            )

            CursoItem(
                nombre = cursos[3].nombre,
                peso = cursos[3].peso,
                nota = notaBD,
                onNotaChange = {
                    notaBD = it
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Redondear promedio final"
                )

                Switch(
                    checked = redondearPromedio,
                    onCheckedChange = {
                        redondearPromedio = it
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = notasConfirmadas,
                    onCheckedChange = {
                        notasConfirmadas = it
                    }
                )

                Text(
                    text = "Confirmo que las notas son correctas"
                )
            }

            Button(
                onClick = {
                    mostrarResultado = true
                },
                enabled = notasConfirmadas,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CALCULAR PROMEDIO")
            }

            if (!mostrarResultado) {

                Text(
                    text = "Asigna las notas y confirma para calcular",
                    color = Color.Gray
                )
            }

            if (mostrarResultado) {

                val promedioPonderado = calcularPromedioPonderado(
                    notaFundamentos = notaFundamentos,
                    notaPOO = notaPOO,
                    notaMoviles = notaMoviles,
                    notaBD = notaBD
                )

                val promedioFinal = if (redondearPromedio) {
                    promedioPonderado.roundToInt().toString()
                } else {
                    "%.2f".format(promedioPonderado)
                }

                val observacion = when {
                    promedioPonderado >= 17 -> "EXCELENTE"
                    promedioPonderado >= 13 -> "APROBADO"
                    promedioPonderado >= 10 -> "EN RECUPERACIÓN"
                    else -> "DESAPROBADO"
                }

                Text(
                    text = "Promedio ponderado: %.2f".format(promedioPonderado)
                )

                Text(
                    text = "Promedio final: $promedioFinal"
                )

                Text(
                    text = "Observación: $observacion"
                )
            }
        }
    }
}