package com.tecsup.rickandmorty.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.rickandmorty.data.repository.ItemRepository
import com.tecsup.rickandmorty.domain.model.Item
import com.tecsup.rickandmorty.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritosViewModel(private val repository: ItemRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Item>>>(UiState.Cargando)
    val uiState = _uiState.asStateFlow()

    init { viewModelScope.launch { repository.favoritos.collect { _uiState.value = UiState.Exito(it) } } }
    fun alternarFavorito(id: Int) { viewModelScope.launch { repository.alternarFavorito(id) } }
}
