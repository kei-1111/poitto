package com.example.domain

import com.example.repository.AuthRepository
import javax.inject.Inject

class GetCurrentFirebaseUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke() = authRepository.getCurrentUser()
}
