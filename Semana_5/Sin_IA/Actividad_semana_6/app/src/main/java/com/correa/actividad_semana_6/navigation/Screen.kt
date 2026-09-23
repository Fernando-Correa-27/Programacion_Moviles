package com.correa.actividad_semana_6.navigation

sealed class Screen(val route: String) {

    object Inicio : Screen(route = "inicio")

    object MisCitas : Screen(route = "misCitas")

    object Historial : Screen(route = "historial")

    object Perfil : Screen(route = "perfil")

    object PerfilMedico : Screen(route = "perfilMedico/{medicoId}") {
        fun createRoute(medicoId: Int): String = "perfilMedico/$medicoId"
    }

    object AgendarCita : Screen(route = "agendarCita/{medicoId}") {
        fun createRoute(medicoId: Int): String = "agendarCita/$medicoId"
    }

    object Confirmacion : Screen(route = "confirmacion/{medicoId}/{fechaIdx}/{horaIdx}") {
        fun createRoute(medicoId: Int, fechaIdx: Int, horaIdx: Int): String =
            "confirmacion/$medicoId/$fechaIdx/$horaIdx"
    }
}