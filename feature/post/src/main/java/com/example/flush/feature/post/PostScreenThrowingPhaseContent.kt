package com.example.flush.feature.post

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.flush.core.designsystem.component.AnimatedText
import com.example.flush.core.designsystem.component.Container
import com.example.flush.core.designsystem.component.Loading
import com.example.flush.core.designsystem.component.TitleSmallText
import com.example.flush.core.designsystem.theme.dimensions.Alpha
import com.example.flush.core.designsystem.theme.dimensions.Paddings
import com.example.flush.core.ui.LocalEngine
import com.example.flush.core.ui.LocalGraphicsView
import com.example.flush.core.ui.LocaleEnvironment
import com.example.flush.core.utils.SceneviewUtils.applyTextureToAlpha
import com.example.flush.core.utils.SceneviewUtils.calculateScaleFromBitmapSize
import com.example.flush.core.utils.SceneviewUtils.createModelNode
import com.example.flush.core.utils.SceneviewUtils.loadTextureBitmap
import com.example.flush.feature.post.PostScreenDimensions.AnimationDuration
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberMainLightNode
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberOnGestureListener
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun PostScreenThrowingPhaseContent(
    uiState: PostUiState,
    onEvent: (PostUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Throwing3DModel(
            uiState = uiState,
            onEvent = onEvent,
        )
        AnimatedVisibility(
            visible = uiState.animationState == PostUiAnimationState.Completed,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            enter = fadeIn(),
        ) {
            ResponseMessageViewer(
                responseMessage = uiState.responseMessage,
                onNavigateToSearch = { onEvent(PostUiEvent.OnResponseMessageViewerClick) },
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.safeDrawing)
                    .padding(Paddings.Large),
            )
        }
    }
}

@Composable
private fun ResponseMessageViewer(
    responseMessage: String,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .clickable {
                onNavigateToSearch()
            },
        containerColor = MaterialTheme.colorScheme.surface.copy(
            alpha = Alpha.Medium,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = Paddings.ExtraSmall,
                    horizontal = Paddings.Medium,
                ),
        ) {
            TitleSmallText(
                text = "天の声",
            )
            AnimatedText(
                text = responseMessage,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private const val CameraNodePositionX = 0.1f
private const val CameraNodePositionY = 8.0f

@Suppress("LongMethod")
@Composable
private fun Throwing3DModel(
    uiState: PostUiState,
    onEvent: (PostUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val engine = LocalEngine.current
    val view = LocalGraphicsView.current
    val environment = LocaleEnvironment.current
    val context = LocalContext.current

    val textureBitmap = loadTextureBitmap(
        context = context,
        imageUri = uiState.imageUri,
        message = uiState.message,
        textColor = MaterialTheme.colorScheme.onSurface.toArgb(),
        backgroundColor = MaterialTheme.colorScheme.surfaceContainer.toArgb(),
    )

    if (textureBitmap == null) {
        Loading()
        return
    }

    val centerNode = rememberNode(engine)

    val modelLoader = rememberModelLoader(engine)

    val modelNode = rememberNode {
        createModelNode(
            engine = engine,
            modelLoader = modelLoader,
            assetFileLocation = "models/plate.glb",
            textureBitmap = textureBitmap,
        )
    }
    val initialScale = calculateScaleFromBitmapSize(textureBitmap)
    val throwAnimationState = remember { ThrowAnimationState(initialScale) }

    val cameraNode = rememberCameraNode(engine) {
        position = Position(x = CameraNodePositionX, y = CameraNodePositionY)
        lookAt(modelNode)
    }

    val mainLightNode = rememberMainLightNode(engine)

    LaunchedEffect(uiState.animationState) {
        if (uiState.animationState == PostUiAnimationState.Running) {
            throwAnimationState.startAnimation()
        }
    }

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
            childNodes = listOf(centerNode, modelNode),
            mainLightNode = mainLightNode,
            onFrame = {
                modelNode.position = Position(
                    x = throwAnimationState.positionX.value,
                    y = throwAnimationState.positionY.value,
                    z = throwAnimationState.positionZ.value,
                )
                modelNode.rotation = Rotation(
                    x = throwAnimationState.rotateX.value,
                    y = throwAnimationState.rotateY.value,
                    z = throwAnimationState.rotateZ.value,
                )
                modelNode.scale = Scale(
                    x = throwAnimationState.scaleX.value,
                    y = throwAnimationState.scaleY.value,
                    z = throwAnimationState.scaleZ.value,
                )
                modelNode.apply {
                    materialInstances.forEach {
                        it.forEach {
                            applyTextureToAlpha(it, throwAnimationState.alpha.value)
                        }
                    }
                }
            },
            onGestureListener = rememberOnGestureListener(
                onSingleTapUp = { event, tapedNode ->
                    if (tapedNode == modelNode) {
                        onEvent(PostUiEvent.OnModelTapped)
                    }
                },
            ),
        )
    }
}

class ThrowAnimationState(
    private val initialScale: Scale,
) {
    private var rotationXTargetValue: Float = generateRandomRotationTargetValue()
    private var rotationYTargetValue: Float = generateRandomRotationTargetValue()
    private var rotationZTargetValue: Float = generateRandomRotationTargetValue()

    private var positionXTargetValue: Float = generateRandomXTargetValue()
    private var positionZTargetValue: Float = generateRandomZTargetValue()

    val positionX = Animatable(initialScale.x / PositonXBaseValue)
    val positionY = Animatable(PositonYInitialValue)
    val positionZ = Animatable(PositonZInitialValue)
    val rotateX = Animatable(RotateXInitialValue)
    val rotateY = Animatable(RotateYInitialValue)
    val rotateZ = Animatable(RotateZInitialValue)
    val scaleX = Animatable(initialScale.x)
    val scaleY = Animatable(initialScale.y)
    val scaleZ = Animatable(initialScale.z)
    val alpha = Animatable(AlphaInitialValue)

    private fun generateRandomRotationTargetValue(): Float {
        return Random.nextFloat() * (MaxRotationTargetValue - MinRotationTargetValue) + MinRotationTargetValue
    }

    private fun generateRandomXTargetValue(): Float {
        return Random.nextFloat() * MaxPositionXTargetValue * 2 + MinPositionXTargetValue
    }

    private fun generateRandomZTargetValue(): Float {
        return Random.nextFloat() * MaxPositionZTargetValue * 2 + MinPositionZTargetValue
    }

    @Suppress("LongMethod")
    suspend fun startAnimation() {
        coroutineScope {
            launch {
                positionX.animateTo(
                    positionXTargetValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
            launch {
                positionY.animateTo(
                    PositionYTargetValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
            launch {
                positionZ.animateTo(
                    positionZTargetValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
            launch {
                rotateX.animateTo(
                    rotationXTargetValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
            launch {
                rotateY.animateTo(
                    rotationYTargetValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
            launch {
                rotateZ.animateTo(
                    rotationZTargetValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
            launch {
                scaleX.animateTo(
                    initialScale.x / ScaleBaseValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
            launch {
                scaleY.animateTo(
                    initialScale.y / ScaleBaseValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
            launch {
                scaleZ.animateTo(
                    initialScale.z / ScaleBaseValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
            launch {
                alpha.animateTo(
                    AlphaTargetValue,
                    animationSpec = tween(durationMillis = AnimationDuration),
                )
            }
        }
    }

    companion object {
        private const val PositonXBaseValue = 21.555f
        private const val PositonYInitialValue = 0f
        private const val PositonZInitialValue = 0f
        private const val RotateXInitialValue = 0f
        private const val RotateYInitialValue = 0f
        private const val RotateZInitialValue = 0f
        private const val AlphaInitialValue = 1f

        private const val MinPositionXTargetValue = -10f
        private const val MaxPositionXTargetValue = 10f
        private const val PositionYTargetValue = -25f
        private const val MinPositionZTargetValue = -5f
        private const val MaxPositionZTargetValue = 5f
        private const val MinRotationTargetValue = 720f
        private const val MaxRotationTargetValue = 1080f
        private const val ScaleBaseValue = 30f
        private const val AlphaTargetValue = 0.0f
    }
}
