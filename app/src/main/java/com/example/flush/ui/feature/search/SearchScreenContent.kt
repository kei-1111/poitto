package com.example.flush.ui.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.flush.domain.model.ThrowingItem
import com.example.flush.ui.compositon_local.LocalEngine
import com.example.flush.ui.compositon_local.LocalGraphicsView
import com.example.flush.ui.compositon_local.LocaleEnvironment
import com.example.flush.ui.utils.SceneviewUtils.createModelNode
import com.example.flush.ui.utils.SceneviewUtils.loadTextureBitmapFromUrl
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberMainLightNode
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberOnGestureListener
import kotlinx.collections.immutable.ImmutableList
import kotlin.random.Random

@Suppress("LongMethod", "MultipleEmitters")
@Composable
fun SearchScreenContent(
    throwingItems: ImmutableList<ThrowingItem>,
    modifier: Modifier = Modifier,
) {
    val engine = LocalEngine.current
    val view = LocalGraphicsView.current
    val environment = LocaleEnvironment.current

    val context = LocalContext.current

    val collectionSystem = rememberCollisionSystem(view)

    if (throwingItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val modelNodes = remember { mutableStateListOf<ModelNode>() }
    val modelLoader = rememberModelLoader(engine)

    throwingItems.forEachIndexed { index, item ->
        val textureBitmap = loadTextureBitmapFromUrl(
            context = context,
            imageUrl = item.imageUrl,
            message = item.message,
            textColor = MaterialTheme.colorScheme.onSurface.toArgb(),
            backgroundColor = MaterialTheme.colorScheme.surface.toArgb(),
        )

        LaunchedEffect(textureBitmap, index) {
            if (textureBitmap != null) {
                val modelNode = createModelNode(
                    engine = engine,
                    modelLoader = modelLoader,
                    assetFileLocation = "models/plate_alpha.glb",
                    textureBitmap = textureBitmap,
                    scaleToUnits = 0.25f,
                )

                modelNode.position = randomPosition()
                modelNode.rotation = randomRotation()

                modelNodes.add(modelNode)
            }
        }
    }

    if (modelNodes.size < throwingItems.size) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val centerNode = rememberNode(engine)

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
            collisionSystem = collectionSystem,
            childNodes = listOf(centerNode) + modelNodes,
            mainLightNode = mainLightNode,
            onFrame = {
            },
            onGestureListener = rememberOnGestureListener(),
        )
    }
}

private const val MinPositionValue = 0
private const val MaxPositionValue = 2

private fun randomPosition() = Position(
    x = Random.nextInt(MinPositionValue, MaxPositionValue).toFloat(),
    y = Random.nextInt(MinPositionValue, MaxPositionValue).toFloat(),
    z = Random.nextInt(MinPositionValue, MaxPositionValue).toFloat(),
)

private const val MinRotationValue = 0
private const val MaxRotationValue = 360

private fun randomRotation() = Position(
    x = Random.nextInt(MinRotationValue, MaxRotationValue).toFloat(),
    y = Random.nextInt(MinRotationValue, MaxRotationValue).toFloat(),
    z = Random.nextInt(MinRotationValue, MaxRotationValue).toFloat(),
)
