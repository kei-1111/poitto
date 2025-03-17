package com.example.flush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.flush.core.designsystem.theme.FlushTheme
import com.example.flush.navigation.FlushNavHost
import com.example.flush.navigation.Screen
import com.example.flush.core.ui.SceneviewProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val startDestination by mainViewModel.startDestination.collectAsStateWithLifecycle()

            SceneviewProvider {
                FlushTheme {
                    startDestination?.let {
                        FlushNavHost(
                            navController = rememberNavController(),
                            startDestination = startDestination ?: Screen.AuthSelection,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}
