package com.example.flush.core.domain

import android.net.Uri
import com.example.flush.core.di.IoDispatcher
import com.example.flush.core.repository.ThrowingItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadThrowingItemImageUseCase @Inject constructor(
    private val throwingItemRepository: ThrowingItemRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(throwingItemId: String, imageUrl: Uri) = withContext(ioDispatcher) {
        throwingItemRepository.uploadImage(throwingItemId, imageUrl)
    }
}
