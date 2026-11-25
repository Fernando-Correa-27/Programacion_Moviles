package com.correa.murocampus.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.correa.murocampus.model.Post
import com.correa.murocampus.ui.theme.AlturaMinimaTactil
import com.correa.murocampus.ui.theme.EspacioL
import com.correa.murocampus.ui.theme.EspacioM
import com.correa.murocampus.ui.theme.EspacioS
import com.correa.murocampus.ui.theme.EspacioXs
import com.correa.murocampus.ui.theme.RadioControl
import com.correa.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    estado: UiState<List<Post>>,
    consulta: String,
    onBuscar: (String) -> Unit,
    usuarioFiltro: Int?,
    onFiltrar: (Int?) -> Unit,
    onReintentar: () -> Unit,
    onClickPost: (Int) -> Unit,
    onCrear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MuroCampus",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCrear,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                },
                text = {
                    Text(
                        text = "Nueva publicación",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(RadioControl)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "Publicaciones de la comunidad",
                modifier = Modifier.padding(horizontal = EspacioL),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = consulta,
                onValueChange = onBuscar,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EspacioL, vertical = EspacioM),
                placeholder = { Text("Buscar por título") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = consulta.isNotEmpty(),
                        enter = fadeIn(tween(160)) + scaleIn(
                            animationSpec = tween(160),
                            initialScale = 0.8f
                        ),
                        exit = fadeOut(tween(120)) + scaleOut(
                            animationSpec = tween(120),
                            targetScale = 0.8f
                        )
                    ) {
                        IconButton(
                            onClick = { onBuscar("") },
                            modifier = Modifier.size(AlturaMinimaTactil)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Limpiar búsqueda"
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(RadioControl),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            Text(
                text = "Filtrar por autor",
                modifier = Modifier.padding(horizontal = EspacioL),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            LazyRow(
                contentPadding = PaddingValues(
                    start = EspacioL,
                    end = EspacioL,
                    top = EspacioS,
                    bottom = EspacioM
                ),
                horizontalArrangement = Arrangement.spacedBy(EspacioS)
            ) {
                item {
                    FiltroUsuario(
                        texto = "Todos",
                        seleccionado = usuarioFiltro == null,
                        onClick = { onFiltrar(null) }
                    )
                }
                items((1..10).toList()) { userId ->
                    FiltroUsuario(
                        texto = "Usuario $userId",
                        seleccionado = usuarioFiltro == userId,
                        onClick = { onFiltrar(userId) }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EspacioL, vertical = EspacioXs),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Publicaciones",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (estado is UiState.Exito && estado.datos.isNotEmpty()) {
                    Text(
                        text = "${estado.datos.size} en total",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
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
                            SinPublicaciones()
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    start = EspacioL,
                                    end = EspacioL,
                                    top = EspacioS,
                                    bottom = 96.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(EspacioM)
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

@Composable
private fun FiltroUsuario(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = seleccionado,
        onClick = onClick,
        label = { Text(texto) },
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
