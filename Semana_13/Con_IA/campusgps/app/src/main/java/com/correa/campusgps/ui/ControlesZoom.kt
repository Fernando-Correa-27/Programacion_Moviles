package com.correa.campusgps.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.correa.campusgps.R

/**
 * Botones flotantes para acercar y alejar el mapa.
 * Cada botón respeta el mínimo táctil de 48 dp en Android.
 */
@Composable
fun ControlesZoom(
    onAcercar: () -> Unit,
    onAlejar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.requiredWidth(48.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shadowElevation = 6.dp
    ) {
        Column {
            IconButton(onClick = onAcercar, modifier = Modifier.size(48.dp)) {
                Icon(
                    painter = painterResource(R.drawable.ic_zoom_in),
                    contentDescription = "Acercar el mapa"
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            IconButton(onClick = onAlejar, modifier = Modifier.size(48.dp)) {
                Icon(
                    painter = painterResource(R.drawable.ic_zoom_out),
                    contentDescription = "Alejar el mapa"
                )
            }
        }
    }
}
