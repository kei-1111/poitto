package com.example.flush.domain.use_case

import com.example.flush.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String) = authRepository.signInWithEmail(email, password)
}
