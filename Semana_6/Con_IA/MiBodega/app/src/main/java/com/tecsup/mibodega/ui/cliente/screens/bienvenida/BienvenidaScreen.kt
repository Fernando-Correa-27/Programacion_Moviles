package com.tecsup.mibodega.ui.cliente.screens.bienvenida

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.R
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.BotonSecundario
import com.tecsup.mibodega.ui.theme.AzulEnlace
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.cliente.modelo.CLAVE_DEMO
import com.tecsup.mibodega.ui.cliente.modelo.USUARIO_DEMO
import com.tecsup.mibodega.ui.cliente.modelo.credencialesDemoValidas

/**
 * Pantalla 1: Registro / Login (mockup "Cliente").
 * No sabe navegar sola: recibe qué hacer por parámetro (callbacks).
 */
@Composable
fun BienvenidaScreen(
    onRegistrarse: () -> Unit,
    onIniciarSesion: () -> Unit,
    onTerminos: () -> Unit
) {
    var mostrarLogin by remember { mutableStateOf(false) }
    var usuario by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var intentoLogin by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.background),
                    endY = 900f
                )
            )
            .safeDrawingPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        IlustracionBodega()

        Spacer(Modifier.height(16.dp))

        TituloMiBodega()

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Tus productos de siempre\nen la puerta de tu casa",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(1f))

        BotonPrimario(
            texto = "Registrarme",
            subtexto = "con mi teléfono",
            icono = rememberVectorPainter(Icons.Default.Phone),
            onClick = onRegistrarse
        )

        Spacer(Modifier.height(12.dp))

        BotonSecundario(
            texto = "Iniciar sesión",
            onClick = { mostrarLogin = true }
        )

        Spacer(Modifier.height(20.dp))

        PieTerminos(onTerminos = onTerminos)

        Spacer(Modifier.height(24.dp))
    }

    if (mostrarLogin) {
        AlertDialog(
            onDismissRequest = { mostrarLogin = false },
            title = { Text("Iniciar sesión") },
            text = {
                Column {
                    Text("Acceso demo: $USUARIO_DEMO / $CLAVE_DEMO")
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") },
                        singleLine = true,
                        isError = intentoLogin && usuario.isBlank(),
                        supportingText = {
                            if (intentoLogin && usuario.isBlank()) Text("Ingresa tu usuario")
                        }
                    )
                    OutlinedTextField(
                        value = clave,
                        onValueChange = { clave = it },
                        label = { Text("Contraseña") },
                        singleLine = true,
                        isError = intentoLogin && clave.isBlank(),
                        supportingText = {
                            if (intentoLogin && clave.isBlank()) Text("Ingresa tu contraseña")
                        }
                    )
                    if (intentoLogin && usuario.isNotBlank() && clave.isNotBlank() &&
                        !credencialesDemoValidas(usuario, clave)
                    ) {
                        Text(
                            "Usuario o contraseña incorrectos",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        intentoLogin = true
                        if (credencialesDemoValidas(usuario, clave)) {
                            mostrarLogin = false
                            onIniciarSesion()
                        }
                    }
                ) {
                    Text("Ingresar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarLogin = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// Sub-composables PRIVADOS: solo los usa esta pantalla, por eso no van a "componentes".

@Composable
private fun IlustracionBodega() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ilustracion_bodega),
            contentDescription = "Ilustración de la bodega",
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
private fun TituloMiBodega() {
    Text(
        text = buildAnnotatedString {
            append("Mi ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) { append("Bodega") }
        },
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun PieTerminos(onTerminos: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Al continuar aceptas nuestros",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Términos y Condiciones",
            style = MaterialTheme.typography.bodySmall,
            color = AzulEnlace,
            modifier = Modifier.clickable(onClick = onTerminos)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BienvenidaPreview() {
    BodegaTheme {
        BienvenidaScreen({}, {}, {})
    }
}

