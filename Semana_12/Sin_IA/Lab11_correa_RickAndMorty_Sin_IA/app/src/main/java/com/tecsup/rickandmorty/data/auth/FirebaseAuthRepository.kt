package com.tecsup.rickandmorty.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tecsup.rickandmorty.domain.model.Rol
import com.tecsup.rickandmorty.domain.model.Sesion
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override suspend fun iniciarSesion(correo: String, clave: String): Result<Sesion> = runCatching {
        auth.signInWithEmailAndPassword(correo, clave).await()
        cargarSesion() ?: error("No se encontró la sesión autenticada")
    }

    override suspend fun sesionActual(): Sesion? = if (auth.currentUser == null) null else cargarSesion()

    private suspend fun cargarSesion(): Sesion? {
        val usuario = auth.currentUser ?: return null
        val documento = firestore.collection("usuarios").document(usuario.uid).get().await()
        val rol = if (documento.getString("rol") == "admin") Rol.ADMIN else Rol.USUARIO
        return Sesion(usuario.uid, usuario.email.orEmpty(), rol)
    }

    override fun cerrarSesion() = auth.signOut()
}
