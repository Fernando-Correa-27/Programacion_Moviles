package com.tecsup.rickandmorty.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tecsup.rickandmorty.ui.UiState

@Composable
fun LoginScreen(
    estado: UiState<*>?, correo: String, clave: String,
    alCambiarCorreo: (String) -> Unit, alCambiarClave: (String) -> Unit,
    alIngresar: () -> Unit, alReintentarSesion: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Rick and Morty", style = MaterialTheme.typography.headlineLarge)
        Text("Catálogo de personajes", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(28.dp))
        OutlinedTextField(correo, alCambiarCorreo, Modifier.fillMaxWidth(), label = { Text("Correo") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(clave, alCambiarClave, Modifier.fillMaxWidth(), label = { Text("Contraseña") }, singleLine = true, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(20.dp))
        Button(onClick = alIngresar, Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), enabled = estado !is UiState.Cargando) {
            Text("Iniciar sesión")
        }
        Spacer(Modifier.height(12.dp))
        when (estado) {
            UiState.Cargando -> CircularProgressIndicator()
            is UiState.Error -> {
                Text(estado.mensaje, color = MaterialTheme.colorScheme.error)
                if (estado.mensaje.contains("sesión", ignoreCase = true)) {
                    Button(onClick = alReintentarSesion) { Text("Reintentar verificación") }
                }
            }
            else -> Unit
        }
    }
}
