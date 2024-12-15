package com.example.flush.domain.use_case

import com.example.flush.di.IoDispatcher
import com.example.flush.domain.model.ThrowingItem
import com.example.flush.domain.repository.ThrowingItemRepository
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
