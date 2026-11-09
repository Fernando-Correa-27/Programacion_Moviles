package com.tecsup.rickandmorty

import com.tecsup.rickandmorty.data.auth.AuthRepository
import com.tecsup.rickandmorty.domain.model.Sesion
import com.tecsup.rickandmorty.ui.UiState
import com.tecsup.rickandmorty.ui.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun camposVaciosNoLlamanAlRepositorio() = runTest {
        val autenticacion = AuthRepositoryDePrueba()
        val viewModel = LoginViewModel(autenticacion)
        advanceUntilIdle()

        viewModel.iniciarSesion("", "clave")
        assertEquals(UiState.Error("Ingresa tu correo y contraseña."), viewModel.uiState.value)
        viewModel.iniciarSesion("persona@example.com", "")

        assertEquals(0, autenticacion.intentosDeInicio)
        assertEquals(UiState.Error("Ingresa tu correo y contraseña."), viewModel.uiState.value)
    }

    private class AuthRepositoryDePrueba : AuthRepository {
        var intentosDeInicio = 0
        override suspend fun iniciarSesion(correo: String, clave: String): Result<Sesion> {
            intentosDeInicio++
            return Result.failure(IllegalArgumentException("No se esperaba este llamado"))
        }
        override suspend fun sesionActual(): Sesion? = null
        override fun cerrarSesion() = Unit
    }
}
