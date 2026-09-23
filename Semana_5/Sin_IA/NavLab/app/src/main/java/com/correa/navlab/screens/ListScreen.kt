package com.correa.navlab.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.correa.navlab.navigation.Screen
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController
) {
    val items = List(8) { index ->
        "Elemento ${index + 1}"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Lista de elementos")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            items(items.size) { index ->

                ListItem(
                    headlineContent = {
                        Text(items[index])
                    },
                    supportingContent = {
                        Text("ID: ${index + 1}")
                    },
                    modifier = Modifier.clickable {
                        navController.navigate(
                            Screen.Detail.createRoute(index + 1)
                        )
                    }
                )

                HorizontalDivider()

            }
        }
    }
}