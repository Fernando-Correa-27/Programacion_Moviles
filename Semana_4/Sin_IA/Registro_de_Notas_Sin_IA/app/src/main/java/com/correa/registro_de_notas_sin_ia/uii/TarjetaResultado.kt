package com.correa.registro_de_notas_sin_ia.uii

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TarjetaResultado(
    promedioPonderado: Double,
    promedioFinal: String,
    observacion: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "RESULTADO"
            )

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