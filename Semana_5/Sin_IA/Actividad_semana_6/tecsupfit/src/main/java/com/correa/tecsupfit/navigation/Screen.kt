package com.correa.tecsupfit.navigation

sealed class Screen(val route: String) {

    object Inicio : Screen(route = "inicio")

    object Reservas : Screen(route = "reservas")

    object Rutinas : Screen(route = "rutinas")

    object Perfil : Screen(route = "perfil")

    object DetalleClase : Screen(route = "detalleClase/{claseId}") {
        fun createRoute(claseId: Int): String = "detalleClase/$claseId"
    }

    object Confirmacion : Screen(route = "confirmacion/{claseId}/{horarioIdx}") {
        fun createRoute(claseId: Int, horarioIdx: Int): String =
            "confirmacion/$claseId/$horarioIdx"
    }
}