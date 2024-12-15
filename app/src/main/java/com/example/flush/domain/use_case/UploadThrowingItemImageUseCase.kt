package com.example.flush.domain.use_case

import android.net.Uri
import com.example.flush.di.IoDispatcher
import com.example.flush.domain.repository.ThrowingItemRepository
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
