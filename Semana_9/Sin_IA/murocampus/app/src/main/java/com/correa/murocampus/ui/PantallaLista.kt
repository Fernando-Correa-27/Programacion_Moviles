package com.correa.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.correa.murocampus.model.Post
import com.correa.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    estado: UiState<List<Post>>,
    usuarioFiltro: Int?,
    onFiltrar: (Int?) -> Unit,
    onReintentar: () -> Unit,
    onClickPost: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MuroCampus",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = usuarioFiltro == null,
                        onClick = { onFiltrar(null) },
                        label = { Text("Todos") }
                    )
                }
                items((1..10).toList()) { userId ->
                    FilterChip(
                        selected = usuarioFiltro == userId,
                        onClick = { onFiltrar(userId) },
                        label = { Text("Usuario $userId") }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when (estado) {
                    UiState.Cargando -> Cargando()

                    is UiState.Error -> ErrorConReintento(
                        mensaje = estado.mensaje,
                        onReintentar = onReintentar
                    )

                    is UiState.Exito -> {
                        if (estado.datos.isEmpty()) {
                            Text(
                                text = "Sin publicaciones",
                                modifier = Modifier.align(Alignment.Center),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(vertical = 8.dp)
                            ) {
                                items(estado.datos, key = { it.id }) { post ->
                                    TarjetaPost(
                                        post = post,
                                        onClick = { onClickPost(post.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
