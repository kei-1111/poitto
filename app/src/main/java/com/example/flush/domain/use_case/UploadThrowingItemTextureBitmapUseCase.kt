package com.example.flush.domain.use_case

import android.graphics.Bitmap
import com.example.flush.di.IoDispatcher
import com.example.flush.domain.repository.ThrowingItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadThrowingItemTextureBitmapUseCase @Inject constructor(
    private val throwingItemRepository: ThrowingItemRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(throwingItemId: String, textureBitmap: Bitmap): Result<String> =
        withContext(ioDispatcher) { throwingItemRepository.uploadTextureBitmap(throwingItemId, textureBitmap)}
}
