package com.example.flush.ui.compositon_local

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.google.android.filament.Engine
import com.google.android.filament.View
import io.github.sceneview.environment.Environment
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberView

@Composable
fun SceneviewProvider(
    content: @Composable () -> Unit,
) {
    val engine = rememberEngine()
    val view = rememberView(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)
    val environment = remember(environmentLoader) {
        environmentLoader.createHDREnvironment("environments/space.hdr")!!
    }

    DisposableEffect(Unit) {
        onDispose {
            engine.destroy()
        }
    }

    CompositionLocalProvider(
        LocalEngine provides engine,
        LocalGraphicsView provides view,
        LocaleEnvironment provides environment,
    ) {
        content()
    }
}

val LocalEngine = compositionLocalOf<Engine> {
    error("Engine not provided")
}

val LocalGraphicsView = compositionLocalOf<View> {
    error("SceneView not provided")
}

val LocaleEnvironment = compositionLocalOf<Environment> {
    error("Environment not provided")
}
