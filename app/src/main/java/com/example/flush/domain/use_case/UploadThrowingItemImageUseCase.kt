package com.example.flush.domain.use_case

import android.net.Uri
import com.example.flush.domain.repository.ThrowingItemRepository
import javax.inject.Inject

class UploadThrowingItemImageUseCase @Inject constructor(
    private val throwingItemRepository: ThrowingItemRepository,
) {
    suspend operator fun invoke(throwingItemId: String, imageUrl: Uri) =
        throwingItemRepository.uploadImage(throwingItemId, imageUrl)
}
