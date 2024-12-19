package com.example.domain

import android.graphics.Bitmap
import com.example.di.IoDispatcher
import com.example.repository.ThrowingItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadThrowingItemTextureBitmapUseCase @Inject constructor(
    private val throwingItemRepository: ThrowingItemRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(throwingItemId: String, textureBitmap: Bitmap) = withContext(ioDispatcher) {
        throwingItemRepository.uploadTextureBitmap(throwingItemId, textureBitmap)
    }
}
