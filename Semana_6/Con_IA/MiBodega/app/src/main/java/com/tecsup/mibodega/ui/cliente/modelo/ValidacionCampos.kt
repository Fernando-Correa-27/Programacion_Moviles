package com.tecsup.mibodega.ui.cliente.modelo

fun camposRequeridosCompletos(vararg valores: String): Boolean =
    valores.all { it.isNotBlank() }
