package com.example.flush.core.utils.ktx

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

private const val Quality = 100

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, Quality, stream)
    return stream.toByteArray()
}
