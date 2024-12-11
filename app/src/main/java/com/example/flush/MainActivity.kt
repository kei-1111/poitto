package com.example.flush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.flush.ui.navigation.FlushNavHost
import com.example.flush.ui.navigation.Screen
import com.example.flush.ui.theme.FlushTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlushTheme {
                FlushNavHost(
                    navController = rememberNavController(),
                    startDestination = Screen.AuthSelection,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
