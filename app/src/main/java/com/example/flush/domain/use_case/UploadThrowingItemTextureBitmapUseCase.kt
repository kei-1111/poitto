package com.example.flush.domain.use_case

import android.graphics.Bitmap
import com.example.flush.domain.repository.ThrowingItemRepository
import javax.inject.Inject

class UploadThrowingItemTextureBitmapUseCase @Inject constructor(
    private val throwingItemRepository: ThrowingItemRepository,
) {
    suspend operator fun invoke(throwingItemId: String, textureBitmap: Bitmap): Result<String> =
        throwingItemRepository.uploadTextureBitmap(throwingItemId, textureBitmap)
}
