package com.correa.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.correa.murocampus.data.PostRepositoryRemote
import com.correa.murocampus.data.remote.RetrofitClient
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
                val estado by viewModel.lista.collectAsStateWithLifecycle()
                PantallaLista(
                    estado = estado,
                    onReintentar = viewModel::cargar
                )
            }
        }
    }
}
