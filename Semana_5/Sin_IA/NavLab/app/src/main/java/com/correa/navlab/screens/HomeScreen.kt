package com.correa.navlab.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.correa.navlab.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Pantalla Tecsup"
        )

        Button(
            onClick = {
                navController.navigate(Screen.List.route)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Ver lista de elementos")
        }

        Button(
            onClick = {
                navController.navigate(Screen.Profile.route)
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Mi perfil")
        }
    }
}