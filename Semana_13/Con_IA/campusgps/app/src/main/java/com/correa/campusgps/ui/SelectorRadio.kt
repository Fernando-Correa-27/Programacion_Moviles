package com.correa.campusgps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Selector exclusivo del radio de cercanía.
 * "Sin radio" equivale a null, que apaga el círculo y las marcas de la lista.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorRadio(
    radioMetros: Int?,
    onElegir: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            "Radio",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(end = 10.dp)
        )
        SingleChoiceSegmentedButtonRow(modifier = Modifier.weight(1f)) {
            OPCIONES_RADIO.forEachIndexed { indice, metros ->
                SegmentedButton(
                    selected = radioMetros == metros,
                    onClick = { onElegir(metros) },
                    shape = SegmentedButtonDefaults.itemShape(index = indice, count = OPCIONES_RADIO.size),
                    label = {
                        Text(
                            metros?.let { "$it m" } ?: "Sin radio",
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1
                        )
                    }
                )
            }
        }
    }
}

private val OPCIONES_RADIO: List<Int?> = listOf(null, 100, 200, 500)
