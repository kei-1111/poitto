package com.example.flush.domain.use_case

import com.example.flush.domain.model.ThrowingItem
import com.example.flush.domain.repository.ThrowingItemRepository
import javax.inject.Inject

class CreateThrowingItemUseCase @Inject constructor(
    private val throwingItemRepository: ThrowingItemRepository,
) {
    suspend operator fun invoke(throwingItem: ThrowingItem) =
        throwingItemRepository.createThrowingItem(throwingItem)
}
