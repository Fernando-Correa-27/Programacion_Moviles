package com.correa.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.correa.murocampus.data.PostRepositoryRemote
import com.correa.murocampus.data.remote.RetrofitClient
import com.correa.murocampus.ui.PantallaCrear
import com.correa.murocampus.ui.PantallaDetalle
import com.correa.murocampus.ui.PantallaLista
import com.correa.murocampus.ui.theme.MurocampusTheme
import com.correa.murocampus.viewmodel.PostsViewModel
import com.correa.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: PostsViewModel by viewModels {
        PostsViewModelFactory(PostRepositoryRemote(RetrofitClient.api))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MurocampusTheme {
                val lista by viewModel.lista.collectAsStateWithLifecycle()
                val detalle by viewModel.detalle.collectAsStateWithLifecycle()
                val idSeleccionado by viewModel.idSeleccionado.collectAsStateWithLifecycle()
                val usuarioFiltro by viewModel.usuarioFiltro.collectAsStateWithLifecycle()
                val creacion by viewModel.creacion.collectAsStateWithLifecycle()
                val eliminacion by viewModel.eliminacion.collectAsStateWithLifecycle()

                var mostrandoCrear by rememberSaveable { mutableStateOf(false) }

                val id = idSeleccionado
                when {
                    mostrandoCrear -> {
                        BackHandler {
                            mostrandoCrear = false
                            viewModel.limpiarCreacion()
                        }
                        PantallaCrear(
                            estado = creacion,
                            onPublicar = viewModel::publicar,
                            onVolver = {
                                mostrandoCrear = false
                                viewModel.limpiarCreacion()
                            }
                        )
                    }

                    id == null -> {
                        PantallaLista(
                            estado = lista,
                            usuarioFiltro = usuarioFiltro,
                            onFiltrar = viewModel::filtrar,
                            onReintentar = viewModel::cargar,
                            onClickPost = viewModel::abrir,
                            onCrear = {
                                viewModel.limpiarCreacion()
                                mostrandoCrear = true
                            }
                        )
                    }

                    else -> {
                        BackHandler { viewModel.volverALista() }
                        PantallaDetalle(
                            estado = detalle,
                            eliminacion = eliminacion,
                            onEliminar = { viewModel.eliminar(id) },
                            onReintentar = { viewModel.abrir(id) },
                            onVolver = viewModel::volverALista
                        )
                    }
                }
            }
        }
    }
}
