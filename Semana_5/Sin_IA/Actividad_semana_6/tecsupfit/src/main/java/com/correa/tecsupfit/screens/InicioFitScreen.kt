package com.correa.tecsupfit.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.correa.tecsupfit.components.ChipFiltro
import com.correa.tecsupfit.components.ClaseCard
import com.correa.tecsupfit.data.clasesFit
import com.correa.tecsupfit.data.filtrosClases

@Composable
fun InicioFitScreen(
    onClaseClick: (Int) -> Unit
) {
    var filtroSeleccionado by remember { mutableStateOf(filtrosClases.first()) }
    val clasesFiltradas = clasesFit.filter { it.dia == filtroSeleccionado }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtrosClases) { filtro ->
                    ChipFiltro(
                        texto = filtro,
                        seleccionado = filtroSeleccionado == filtro,
                        onClick = { filtroSeleccionado = filtro }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Clases disponibles",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(clasesFiltradas, key = { it.id }) { clase ->
            ClaseCard(
                clase = clase,
                onClick = { onClaseClick(clase.id) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }
    }
}