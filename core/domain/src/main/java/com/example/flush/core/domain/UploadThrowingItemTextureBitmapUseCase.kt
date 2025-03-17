package com.example.flush.core.domain

import android.graphics.Bitmap
import com.example.flush.core.di.IoDispatcher
import com.example.flush.core.repository.ThrowingItemRepository
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
