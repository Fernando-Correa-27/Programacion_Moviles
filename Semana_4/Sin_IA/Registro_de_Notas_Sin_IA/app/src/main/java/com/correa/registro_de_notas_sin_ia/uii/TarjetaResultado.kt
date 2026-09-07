package com.correa.registro_de_notas_sin_ia.uii

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TarjetaResultado(
    promedioPonderado: Double,
    promedioFinal: String,
    observacion: String,
    fueRedondeado: Boolean
) {

    val (colorTexto, colorFondo) = when (observacion) {
        "EXCELENTE", "APROBADO" -> VerdeAprobado to VerdeClaro
        "EN RECUPERACIÓN" -> AmarilloRecuperacion to AmarilloClaro
        else -> RojoDesaprobado to RojoClaro
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PurpuraClaro
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = "Promedio ponderado: %.2f".format(promedioPonderado)
            )

            Text(
                text = "Promedio final: $promedioFinal",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PurpuraPrincipal
            )

            if (fueRedondeado) {
                Text(
                    text = "(redondeado)",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(colorFondo)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = observacion,
                    color = colorTexto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}