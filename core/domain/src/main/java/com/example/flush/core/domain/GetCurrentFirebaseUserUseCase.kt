package com.example.flush.core.domain

import com.example.flush.core.repository.AuthRepository
import javax.inject.Inject

class GetCurrentFirebaseUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke() = authRepository.getCurrentUser()
}
