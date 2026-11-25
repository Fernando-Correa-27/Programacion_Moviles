package com.tecsup.mibodega.ui.cliente.modelo

const val USUARIO_DEMO = "cliente"
const val CLAVE_DEMO = "1234"

fun credencialesDemoValidas(usuario: String, clave: String): Boolean =
    usuario.trim() == USUARIO_DEMO && clave == CLAVE_DEMO
