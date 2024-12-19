package com.example.domain

import com.example.di.IoDispatcher
import com.example.model.ThrowingItem
import com.example.repository.ThrowingItemRepository
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
