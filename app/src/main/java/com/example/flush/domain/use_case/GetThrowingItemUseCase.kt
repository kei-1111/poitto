package com.example.flush.domain.use_case

import com.example.flush.di.IoDispatcher
import com.example.flush.domain.repository.ThrowingItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetThrowingItemUseCase @Inject constructor(
    private val throwingItemRepository: ThrowingItemRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) {
    suspend operator fun invoke() = withContext(ioDispatcher) {
        throwingItemRepository.getThrowingItems()
    }
}
