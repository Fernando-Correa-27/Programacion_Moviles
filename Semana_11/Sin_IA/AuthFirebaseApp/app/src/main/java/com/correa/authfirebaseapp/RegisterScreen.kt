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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    AuthPage {
        AuthBrandHeader()
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Crea tu cuenta",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Completa tus datos para empezar.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))

        AuthCard {
            Text("Registro de usuario", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo electrónico",
                keyboardType = KeyboardType.Email
            )
            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                isPassword = true
            )
            AuthTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar contraseña",
                isPassword = true
            )
            Text(
                text = "La contraseña debe tener al menos 6 caracteres.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            AuthPrimaryButton(
                text = "Crear cuenta",
                loadingText = "Creando cuenta…",
                isLoading = isLoading
            ) {
                when {
                    email.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
                        Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                    !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() ->
                        Toast.makeText(context, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
                    password != confirmPassword ->
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    password.length < 6 ->
                        Toast.makeText(context, "Mínimo 6 caracteres", Toast.LENGTH_SHORT).show()
                    else -> {
                        isLoading = true
                        auth.createUserWithEmailAndPassword(email.trim(), password).addOnCompleteListener { task ->
                            isLoading = false
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Usuario creado", Toast.LENGTH_SHORT).show()
                                onRegisterSuccess()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error: ${task.exception?.localizedMessage ?: "No se pudo crear la cuenta"}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
            AuthLink(text = "← Volver al inicio de sesión", onClick = onNavigateBack)
        }
    }
}
