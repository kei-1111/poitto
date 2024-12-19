package com.example.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

data object BitmapUtils {

    private const val FullWidthSpace = "　"
    private const val TextSize = 100f
    private const val MaxCharsPerLine = 20
    private const val CanvasPadding = 100f
    private const val LineSpacing = 10
    private const val ImageVerticalPadding = 100
    private const val CornerRadius = 20f

    private const val TAG = "BitmapUtils"

    fun uriToBitmap(context: Context, uri: Uri?): Bitmap {
        return try {
            val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
            BitmapFactory.decodeStream(inputStream).also { inputStream?.close() }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load image from uri: $uri", e)
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground)
        }
    }

    suspend fun urlToBitmap(context: Context, imageUrl: String?): Bitmap? {
        return try {
            val request = ImageRequest.Builder(context).data(imageUrl).build()
            val result = context.imageLoader.execute(request)
            if (result is SuccessResult) {
                (result.drawable as? BitmapDrawable)?.bitmap
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load image from url: $imageUrl", e)
            null
        }
    }

    fun createTextureBitmap(
        text: String,
        imageBitmap: Bitmap?,
        textColor: Int,
        backgroundColor: Int,
    ): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = TextSize
            color = textColor
            textAlign = Paint.Align.LEFT
            typeface = Typeface.DEFAULT_BOLD
        }

        val lines = processText(text, MaxCharsPerLine)

        val width = (paint.measureText(FullWidthSpace.repeat(MaxCharsPerLine)) + CanvasPadding).toInt()

        val lineHeightList = mutableListOf<Int>()
        for (lineText in lines) {
            val textBounds = Rect()
            paint.getTextBounds(lineText, 0, lineText.length, textBounds)
            val lineHeight = textBounds.height()
            lineHeightList.add(lineHeight + LineSpacing)
        }
        val textHeight = lineHeightList.sum()
        val imageMaxWidth = width - CanvasPadding
        val resizedBitmap = imageBitmap?.let {
            resizeBitmap(
                imageBitmap,
                imageMaxWidth,
            )
        }
        val height = (
            textHeight +
                ((resizedBitmap?.height?.plus(ImageVerticalPadding)) ?: 0) +
                CanvasPadding
            ).toInt()

        val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        canvas.drawColor(backgroundColor)

        for ((i, lineText) in lines.withIndex()) {
            val x = CanvasPadding / 2f
            val y = -paint.ascent() + CanvasPadding / 2f + lineHeightList.take(i).sum()

            canvas.drawText(lineText, x, y, paint)
        }

        resizedBitmap ?: return image

        canvas.drawBitmap(
            resizedBitmap,
            CanvasPadding / 2f,
            textHeight + ImageVerticalPadding.toFloat(),
            null,
        )

        return image
    }

    private fun resizeBitmap(
        originalBitmap: Bitmap,
        maxWidth: Float,
    ): Bitmap {
        val bitmapToUse = if (originalBitmap.config == Bitmap.Config.HARDWARE) {
            originalBitmap.copy(Bitmap.Config.ARGB_8888, false)
        } else {
            originalBitmap
        }

        val originalWidth = bitmapToUse.width
        val originalHeight = bitmapToUse.height

        val widthScale = maxWidth / originalWidth

        val scaledWidth = (originalWidth * widthScale).toInt()
        val scaledHeight = (originalHeight * widthScale).toInt()

        val scaledBitmap = Bitmap.createScaledBitmap(bitmapToUse, scaledWidth, scaledHeight, true)

        val outputBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rectF = RectF(0f, 0f, scaledWidth.toFloat(), scaledHeight.toFloat())

        canvas.drawRoundRect(rectF, CornerRadius, CornerRadius, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(scaledBitmap, 0f, 0f, paint)

        return outputBitmap
    }

    private fun processText(text: String, maxCharsPerLine: Int): List<String> {
        val lines = text.split("\n")
        val result = mutableListOf<String>()

        for (line in lines) {
            result.addAll(line.chunked(maxCharsPerLine))
        }

        return result
    }
}
