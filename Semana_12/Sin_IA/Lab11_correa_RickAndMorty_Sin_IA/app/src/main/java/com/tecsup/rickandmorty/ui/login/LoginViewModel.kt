package com.tecsup.rickandmorty.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.tecsup.rickandmorty.data.auth.AuthRepository
import com.tecsup.rickandmorty.domain.model.Sesion
import com.tecsup.rickandmorty.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Sesion?>>(UiState.Cargando)
    val uiState = _uiState.asStateFlow()

    init { revisarSesion() }

    fun revisarSesion() {
        _uiState.value = UiState.Cargando
        viewModelScope.launch {
            _uiState.value = try {
                UiState.Exito(authRepository.sesionActual())
            } catch (error: Exception) {
                UiState.Error(mensajeError(error, leyendoSesion = true))
            }
        }
    }

    fun iniciarSesion(correo: String, clave: String) {
        if (correo.isBlank() || clave.isBlank()) {
            _uiState.value = UiState.Error("Ingresa tu correo y contraseña.")
            return
        }
        _uiState.value = UiState.Cargando
        viewModelScope.launch {
            _uiState.value = authRepository.iniciarSesion(correo.trim(), clave)
                .fold({ UiState.Exito(it) }, { UiState.Error(mensajeError(it)) })
        }
    }

    fun cerrarSesion() {
        authRepository.cerrarSesion()
        _uiState.value = UiState.Exito(null)
    }

    private fun mensajeError(error: Throwable, leyendoSesion: Boolean = false): String = when {
        error is IOException -> "No hay conexión a internet. Revisa tu conexión e inténtalo de nuevo."
        error is FirebaseAuthInvalidCredentialsException || error is FirebaseAuthInvalidUserException -> "El correo o la contraseña son incorrectos."
        leyendoSesion -> "No se pudo verificar tu sesión. Comprueba tu conexión e inténtalo de nuevo."
        else -> "No se pudo iniciar sesión. Verifica tus datos e inténtalo de nuevo."
    }
}
