package com.tecsup.rickandmorty

import com.tecsup.rickandmorty.notification.Notificador

class FakeNotificador : Notificador {
    val mensajes = mutableListOf<Pair<String, String>>()

    override fun mostrar(titulo: String, texto: String) {
        mensajes += titulo to texto
    }
}
