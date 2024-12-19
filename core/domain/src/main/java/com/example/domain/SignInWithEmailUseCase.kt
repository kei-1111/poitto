package com.example.domain

import com.example.di.IoDispatcher
import com.example.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(email: String, password: String) = withContext(ioDispatcher) {
        authRepository.signInWithEmail(email, password)
    }
}
