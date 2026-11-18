package com.correa.centrotareas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.correa.centrotareas.data.TareasRepositoryLocal

class TareasViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TareasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TareasViewModel(TareasRepositoryLocal()) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
