package com.correa.avisoscampus.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.correa.avisoscampus.data.AvisosRepository

fun avisosViewModelFactory(repo: AvisosRepository): ViewModelProvider.Factory = viewModelFactory {
    initializer { AvisosViewModel(repo) }
}
