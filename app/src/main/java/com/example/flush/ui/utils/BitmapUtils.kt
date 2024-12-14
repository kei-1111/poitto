package com.example.flush.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.flush.R
import kotlin.math.roundToInt

data object BitmapUtils {

    private const val FullWidthSpace = "　"
    private const val TextSize = 100f
    private const val MaxCharsPerLine = 20
    private const val MaxLines = 25
    private const val CanvasPadding = 100f
    private const val LineSpacing = 5f
    private const val ImageHorizontalPadding = 100f
    private const val ImageVerticalPadding = 200f
    private const val CornerRadius = 20f

    private const val TAG = "BitmapUtils"

    fun uriToBitmap(context: Context, uri: Uri?): Bitmap {
        return try {
            val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
            BitmapFactory.decodeStream(inputStream).also { inputStream?.close() }
        } catch (e: Exception) {
            Log.e("BitmapUtils.uriToBitmap", "Failed to load image from uri: $uri", e)
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
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }

        val chunkedText = text.chunked(MaxCharsPerLine).joinToString("\n")
        val lines = chunkedText.split("\n")

        val truncatedAndPaddedLines = lines.take(MaxLines).map { line ->
            val truncated =
                if (line.length > MaxCharsPerLine) line.substring(0, MaxCharsPerLine) else line
            truncated + FullWidthSpace.repeat(MaxCharsPerLine - truncated.length)
        }.toMutableList()

        while (truncatedAndPaddedLines.size < MaxLines) {
            truncatedAndPaddedLines.add(FullWidthSpace.repeat(MaxCharsPerLine))
        }

        val testStringForWidth = FullWidthSpace.repeat(MaxCharsPerLine)
        val width = (paint.measureText(testStringForWidth) + CanvasPadding).toInt()

        val fontMetrics = paint.fontMetrics
        val lineHeight = (-fontMetrics.ascent + fontMetrics.descent + LineSpacing)
        val height = (lineHeight * MaxLines + CanvasPadding).toInt()

        val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        canvas.drawColor(backgroundColor)

        val baseLine = -paint.ascent() + CanvasPadding

        for ((i, lineText) in truncatedAndPaddedLines.withIndex()) {
            val x = width / 2f
            val y = baseLine + (lineHeight * i)
            canvas.drawText(lineText, x, y, paint)
        }

        val textHeight = (lineHeight * lines.size)
        val imageMaxHeight = height - textHeight - ImageVerticalPadding * 2
        val imageMaxWidth = width - ImageHorizontalPadding * 2
        imageBitmap ?: return image
        val resizedBitmap = resizeBitmapToMaxSizeWithRoundedCorners(
            imageBitmap,
            imageMaxWidth.roundToInt(),
            imageMaxHeight.roundToInt(),
            CornerRadius,
        )
        canvas.drawBitmap(
            resizedBitmap,
            imageMaxWidth / 2f - resizedBitmap.width / 2f + ImageHorizontalPadding,
            textHeight + ImageVerticalPadding,
            null,
        )

        return image
    }

    private fun resizeBitmapToMaxSizeWithRoundedCorners(
        originalBitmap: Bitmap,
        maxWidth: Int,
        maxHeight: Int,
        cornerRadius: Float, // 角丸の半径
    ): Bitmap {
        val bitmapToUse = if (originalBitmap.config == Bitmap.Config.HARDWARE) {
            originalBitmap.copy(Bitmap.Config.ARGB_8888, false)
        } else {
            originalBitmap
        }

        val originalWidth = bitmapToUse.width
        val originalHeight = bitmapToUse.height

        val widthScale = maxWidth.toFloat() / originalWidth
        val heightScale = maxHeight.toFloat() / originalHeight
        val scaleFactor = minOf(widthScale, heightScale)

        val scaledWidth = (originalWidth * scaleFactor).toInt()
        val scaledHeight = (originalHeight * scaleFactor).toInt()

        val scaledBitmap = Bitmap.createScaledBitmap(bitmapToUse, scaledWidth, scaledHeight, true)

        val outputBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rectF = RectF(0f, 0f, scaledWidth.toFloat(), scaledHeight.toFloat())

        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(scaledBitmap, 0f, 0f, paint)

        return outputBitmap
    }
}
