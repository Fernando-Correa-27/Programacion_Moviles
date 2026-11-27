package com.tecsup.rickandmorty

import com.tecsup.rickandmorty.domain.model.Item
import com.tecsup.rickandmorty.ui.UiState
import com.tecsup.rickandmorty.ui.lista.ListaViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ListaViewModelTest {
    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    private val personajes = listOf(
        Item(1, "Rick Sanchez", "Humano · Vivo", "https://example.org/rick.png", false),
        Item(2, "Morty Smith", "Humano · Vivo", "https://example.org/morty.png", false)
    )

    @Test
    fun refrescoExitoso_muestraListaYNotificaCantidad() = runTest {
        val repositorio = FakeItemRepository(Result.success(2), 2, personajes)
        val notificador = FakeNotificador()
        val viewModel = ListaViewModel(repositorio, notificador)

        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue(estado is UiState.Exito)
        assertEquals(personajes, (estado as UiState.Exito).datos)
        assertEquals(listOf("Datos actualizados" to "Se guardaron 2 elementos"), notificador.mensajes)
    }

    @Test
    fun refrescoFallidoSinDatos_muestraErrorDeConexion() = runTest {
        val repositorio = FakeItemRepository(Result.failure(IOException()), 0, emptyList())
        val viewModel = ListaViewModel(repositorio, FakeNotificador())

        advanceUntilIdle()

        assertEquals(UiState.Error("No hay conexión a internet"), viewModel.uiState.value)
    }

    @Test
    fun refrescoFallidoConDatos_mantieneListaYMuestraAviso() = runTest {
        val repositorio = FakeItemRepository(Result.failure(IOException()), 2, personajes)
        val viewModel = ListaViewModel(repositorio, FakeNotificador())

        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue(estado is UiState.Exito)
        assertEquals(personajes, (estado as UiState.Exito).datos)
        assertEquals("Sin conexión: mostrando datos guardados", viewModel.avisoSinConexion.value)
    }
}
