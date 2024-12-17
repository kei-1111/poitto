package com.example.flush.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.flush.domain.model.ThrowingItem
import com.github.michaelbull.result.Result

interface ThrowingItemRepository {
    suspend fun getThrowingItems(): Result<List<ThrowingItem>, String>
    suspend fun createThrowingItem(throwingItem: ThrowingItem): Result<Unit, String>
    suspend fun uploadImage(throwingItemId: String, imageUrl: Uri): Result<String, String>
    suspend fun uploadTextureBitmap(throwingItemId: String, textureBitmap: Bitmap): Result<String, String>
}
