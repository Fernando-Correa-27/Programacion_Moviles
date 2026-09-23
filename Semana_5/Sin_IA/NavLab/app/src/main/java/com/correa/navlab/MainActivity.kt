package com.correa.navlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.correa.navlab.navigation.AppNavigation
import com.correa.navlab.ui.theme.NavLabTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavLabTheme {
                AppNavigation()
            }
        }
    }
}