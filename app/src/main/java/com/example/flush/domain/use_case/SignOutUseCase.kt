package com.example.flush.domain.use_case

import com.example.flush.di.IoDispatcher
import com.example.flush.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke() = withContext(ioDispatcher) {
        authRepository.signOut()
    }
}
