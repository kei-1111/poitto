package com.example.flush.domain.use_case

import com.example.flush.domain.repository.ThrowingItemRepository
import javax.inject.Inject

class GetThrowingItemUseCase @Inject constructor(
    private val throwingItemRepository: ThrowingItemRepository,
) {
    suspend operator fun invoke() = throwingItemRepository.getThrowingItems()
}
