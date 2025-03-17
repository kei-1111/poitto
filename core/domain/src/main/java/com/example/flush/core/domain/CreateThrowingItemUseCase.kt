package com.example.flush.core.domain

import com.example.flush.core.di.IoDispatcher
import com.example.flush.core.model.ThrowingItem
import com.example.flush.core.repository.ThrowingItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateThrowingItemUseCase @Inject constructor(
    private val throwingItemRepository: ThrowingItemRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(throwingItem: ThrowingItem) = withContext(ioDispatcher) {
        throwingItemRepository.createThrowingItem(throwingItem)
    }
}
