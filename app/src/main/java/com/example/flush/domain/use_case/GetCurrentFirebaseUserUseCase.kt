package com.example.flush.domain.use_case

import com.example.flush.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentFirebaseUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke() = authRepository.getCurrentUser()
}
