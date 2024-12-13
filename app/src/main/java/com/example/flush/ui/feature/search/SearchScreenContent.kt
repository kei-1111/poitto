package com.example.flush.ui.feature.search

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.flush.ui.compositon_local.LocalEngine
import com.example.flush.ui.compositon_local.LocalGraphicsView
import com.example.flush.ui.compositon_local.LocaleEnvironment
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberMainLightNode
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberOnGestureListener

@Composable
fun SearchScreenContent(
    uiState: SearchUiState,
    onEvent: (SearchUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val engine = LocalEngine.current
    val view = LocalGraphicsView.current
    val environment = LocaleEnvironment.current

    val context = LocalContext.current

//    val textureBitmap = loadTextureBitmap(
//        context = context,
//        imageUri = uiState.imageUri,
//        message = uiState.message,
//        textColor = MaterialTheme.colorScheme.onSurface.toArgb(),
//        backgroundColor = MaterialTheme.colorScheme.surfaceContainer.toArgb(),
//    )
//
//    if (textureBitmap == null) {
//        Box(
//            modifier = modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center,
//        ) {
//            CircularProgressIndicator()
//        }
//        return
//    }

    val centerNode = rememberNode(engine)

    val modelLoader = rememberModelLoader(engine)

//    val modelNode = rememberNode {
//        createModelNode(
//            engine = engine,
//            modelLoader = modelLoader,
//            assetFileLocation = "models/plate_alpha.glb",
//            textureBitmap = textureBitmap,
//            scaleToUnits = 0.25f,
//        )
//    }

    val cameraNode = rememberCameraNode(engine) {
        lookAt(centerNode)
    }

    val mainLightNode = rememberMainLightNode(engine)

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Scene(
            modifier = Modifier.fillMaxSize(),
            engine = engine,
            modelLoader = modelLoader,
            view = view,
            environment = environment,
            cameraNode = cameraNode,
            childNodes = listOf(centerNode),
            mainLightNode = mainLightNode,
            onFrame = {
            },
            onGestureListener = rememberOnGestureListener(

            ),
        )
    }
}