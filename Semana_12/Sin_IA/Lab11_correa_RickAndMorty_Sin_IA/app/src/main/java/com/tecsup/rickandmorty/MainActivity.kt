package com.tecsup.rickandmorty

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.tecsup.rickandmorty.ui.navigation.AppNav
import com.tecsup.rickandmorty.ui.theme.Lab11_correa_RickAndMorty_Sin_IATheme

class MainActivity : ComponentActivity() {
    private val pedirPermisoNotificaciones = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
    private val container by lazy { AppContainer(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            pedirPermisoNotificaciones.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        setContent {
            Lab11_correa_RickAndMorty_Sin_IATheme {
                AppNav(container)
            }
        }
    }
}
