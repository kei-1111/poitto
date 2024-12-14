package com.example.flush.domain.repository

import android.net.Uri
import com.example.flush.domain.model.ThrowingItem

interface ThrowingItemRepository {
    suspend fun getThrowingItems(): Result<List<ThrowingItem>>
    suspend fun createThrowingItem(throwingItem: ThrowingItem): Result<Unit>
    suspend fun uploadImage(throwingItemId: String, imageUrl: Uri): Result<String>
}
