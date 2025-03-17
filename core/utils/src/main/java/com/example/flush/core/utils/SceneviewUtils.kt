package com.example.flush.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import com.example.flush.core.utils.BitmapUtils.createTextureBitmap
import com.example.flush.core.utils.BitmapUtils.uriToBitmap
import com.google.android.filament.Engine
import com.google.android.filament.MaterialInstance
import com.google.android.filament.Texture
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.material.setTexture
import io.github.sceneview.math.Scale
import io.github.sceneview.node.ModelNode
import java.nio.ByteBuffer

data object SceneviewUtils {

    private const val BaseScaleValue = 0.005f

    fun createModelNode(
        engine: Engine,
        modelLoader: ModelLoader,
        assetFileLocation: String,
        textureBitmap: Bitmap,
        id: String = "",
    ): ModelNode {
        // カスタムテクスチャを生成
        val customTexture = createTexture(engine, textureBitmap)

        // モデルインスタンスを作成し、テクスチャを適用
        val modelInstance = modelLoader.createModelInstance(assetFileLocation).apply {
            materialInstances.forEach { applyTextureToMaterial(it, customTexture) }
        }

        // ModelNode を作成して位置とスケールを設定
        return ModelNode(
            modelInstance = modelInstance,
        ).apply {
            scale = calculateScaleFromBitmapSize(textureBitmap)
            name = id
        }
    }

    fun calculateScaleFromBitmapSize(
        textureBitmap: Bitmap,
    ): Scale {
        return Scale(textureBitmap.height * BaseScaleValue, 1f, textureBitmap.width * BaseScaleValue)
    }

    @Composable
    fun loadTextureBitmap(
        context: Context,
        imageUri: Uri?,
        message: String,
        textColor: Int,
        backgroundColor: Int,
    ): Bitmap? {
        // 非同期で画像ビットマップをロード
        val imageBitmap by produceState<Bitmap?>(initialValue = null, imageUri) {
            value = uriToBitmap(context, imageUri)
        }

        // 非同期でテクスチャビットマップを作成
        val textureBitmap by produceState<Bitmap?>(initialValue = null, imageBitmap, message) {
            value = createTextureBitmap(
                text = message,
                imageBitmap = imageBitmap,
                textColor = textColor,
                backgroundColor = backgroundColor,
            )
        }

        return textureBitmap
    }

    fun applyTextureToAlpha(materialInstance: MaterialInstance, alpha: Float) {
        materialInstance.setParameter("baseColorFactor", 1.0f, 1.0f, 1.0f, alpha)
    }

    private fun applyTextureToMaterial(materialInstance: MaterialInstance, texture: Texture) {
        materialInstance.setTexture("baseColorMap", texture)
    }

    private fun createTexture(engine: Engine, bitmap: Bitmap): Texture {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val texture = Texture.Builder()
            .width(mutableBitmap.width)
            .height(mutableBitmap.height)
            .levels(1)
            .sampler(Texture.Sampler.SAMPLER_2D)
            .format(Texture.InternalFormat.SRGB8_A8)
            .build(engine)

        val buffer = ByteBuffer.allocateDirect(mutableBitmap.byteCount)
        mutableBitmap.copyPixelsToBuffer(buffer)
        buffer.flip()
        texture.setImage(
            engine,
            0,
            Texture.PixelBufferDescriptor(
                buffer,
                Texture.Format.RGBA,
                Texture.Type.UBYTE,
            ),
        )
        buffer.clear()
        return texture
    }
}
