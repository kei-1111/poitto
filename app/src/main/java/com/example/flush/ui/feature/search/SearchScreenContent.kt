package com.example.flush.ui.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.flush.ui.compositon_local.LocalEngine
import com.example.flush.ui.compositon_local.LocalGraphicsView
import com.example.flush.ui.compositon_local.LocaleEnvironment
import com.example.flush.ui.utils.BitmapUtils.urlToBitmap
import com.example.flush.ui.utils.SceneviewUtils.createModelNode
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberMainLightNode
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberOnGestureListener
import kotlin.random.Random

private const val SpeedFactor = 0.1f
private const val MinPositionValue = -2f
private const val MaxPositionValue = 2f

@Suppress("LongMethod", "MultipleEmitters")
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

    val collectionSystem = rememberCollisionSystem(view)

    val modelLoader = rememberModelLoader(engine)
    val modelNodes = remember { mutableStateListOf<Pair<ModelNode, Direction>>() } // ModelNodeと方向ベクトルをペアで保持

    uiState.throwingItems.forEachIndexed { index, item ->
        LaunchedEffect(index) {
            urlToBitmap(context, item.textureUrl)?.let {
                val modelNode = createModelNode(
                    engine = engine,
                    modelLoader = modelLoader,
                    assetFileLocation = "models/plate_alpha.glb",
                    id = item.id,
                    textureBitmap = it,
                    scaleToUnits = 0.25f,
                )

                modelNode.position = randomPosition()
                modelNode.rotation = randomRotation()

                val randomDirection = Direction(
                    x = Random.nextFloat() * 0.01f,
                    y = Random.nextFloat() * 0.01f,
                    z = 0f,
                )

                modelNodes.add(modelNode to randomDirection)
            }
        }
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
            childNodes = listOf(centerNode) + modelNodes.map { it.first },
            mainLightNode = mainLightNode,
            onFrame = {
                modelNodes.forEachIndexed { index, (node, direction) ->
                    // 現在の位置を取得
                    val currentPosition = node.position

                    // スケーリングファクタを適用して新しい位置を計算
                    val scaledDirection = direction.toPosition() * SpeedFactor
                    val newPosition = currentPosition + scaledDirection

                    // 範囲チェック (例えば -1.0f～1.0f の範囲内に制約)
                    node.position = Position(
                        x = newPosition.x.coerceIn(MinPositionValue, MaxPositionValue),
                        y = newPosition.y.coerceIn(MinPositionValue, MaxPositionValue),
                        z = newPosition.z.coerceIn(MinPositionValue, MaxPositionValue),
                    )

                    // 回転速度を適用
                    node.rotation.x += direction.rotationSpeed * SpeedFactor
                    node.rotation.y += direction.rotationSpeed * SpeedFactor

                    // 範囲外に出たら方向を反転
                    if (newPosition.x <= MinPositionValue || newPosition.x >= MaxPositionValue) {
                        modelNodes[index] = node to direction.copy(x = -direction.x)
                    }
                    if (newPosition.y <= MinPositionValue || newPosition.y >= MaxPositionValue) {
                        modelNodes[index] = node to direction.copy(y = -direction.y)
                    }
                }
            },
            onGestureListener = rememberOnGestureListener(
                onSingleTapUp = { event, tapedNode ->
                    if (modelNodes.map { it.first }.contains(tapedNode)) {
                        val selectedThrowingItemId = modelNodes.find { it.first.name == tapedNode?.name }
                        onEvent(SearchUiEvent.OnModelTap(selectedThrowingItemId?.first?.name))
                    }
                },
            ),
        )
    }
}

data class Direction(
    val x: Float,
    val y: Float,
    val z: Float,
    val rotationSpeed: Float = 1f, // 回転速度のプロパティを追加
) {
    fun toPosition(): Position {
        return Position(x, y, z)
    }
}

private fun randomPosition() = Position(
    x = Random.nextFloatInRange(MinPositionValue, MaxPositionValue),
    y = Random.nextFloatInRange(MinPositionValue, MaxPositionValue),
    z = Random.nextFloatInRange(MinPositionValue, MaxPositionValue),
)

private fun Random.nextFloatInRange(min: Float, max: Float): Float {
    return min + this.nextFloat() * (max - min)
}

private const val MinRotationValue = 0
private const val MaxRotationValue = 360

private fun randomRotation() = Position(
    x = Random.nextInt(MinRotationValue, MaxRotationValue).toFloat(),
    y = Random.nextInt(MinRotationValue, MaxRotationValue).toFloat(),
    z = Random.nextInt(MinRotationValue, MaxRotationValue).toFloat(),
)
