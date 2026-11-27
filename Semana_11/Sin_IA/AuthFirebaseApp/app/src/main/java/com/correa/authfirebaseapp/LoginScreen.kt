package com.correa.authfirebaseapp

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    AuthPage {
        AuthBrandHeader()
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Bienvenido de nuevo",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Inicia sesión para continuar con tus actividades.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))

        AuthCard {
            Text("Iniciar sesión", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo electrónico",
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email
            )
            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                isPassword = true
            )
            AuthPrimaryButton(
                text = "Ingresar",
                loadingText = "Ingresando…",
                isLoading = isLoading
            ) {
                when {
                    email.isBlank() || password.isBlank() ->
                        Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                    !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() ->
                        Toast.makeText(context, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
                    else -> {
                        isLoading = true
                        auth.signInWithEmailAndPassword(email.trim(), password).addOnCompleteListener { task ->
                            isLoading = false
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Inicio exitoso", Toast.LENGTH_SHORT).show()
                                onLoginSuccess()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error: ${task.exception?.localizedMessage ?: "No se pudo iniciar sesión"}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
            AuthLink(text = "¿No tienes cuenta? Regístrate aquí", onClick = onNavigateToRegister)
        }

        Spacer(Modifier.height(28.dp))
        Text(
            text = "Fernando Correa Huincho · Tecsup",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
